package base.quarter;

import base.course.Course;
import base.course.CourseRepository;
import base.flowchart.Flowchart;
import base.flowchart.FlowchartRepository;
import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/quarter")
public class QuarterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlowchartRepository flowchartRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ArrayList<Quarter> listAll(@CurrentUser UserDetails curUser) {
        final ArrayList<Quarter> quarters = new ArrayList<>();
        if (User.isAdmin(curUser)) {
            quarterRepository.findAll().forEach(quarter -> quarters.add(quarter));
        }
        return quarters;
    }

    @GetMapping("{id}")
    public Quarter find(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        final User user = userRepository.findByEmail(curUser.getUsername());

        if (user != null && (User.isAdmin(curUser) || user.hasQuarter(id))) {
            return quarterRepository.findOne(id);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Quarter> create(@CurrentUser UserDetails curUser, @RequestBody Quarter input) {
        final User user = userRepository.findByEmail(curUser.getUsername());

        if (user != null) {
            Quarter quarter = new Quarter();
            quarter.setQuarter(input.getQuarter());

            Flowchart flowchart = flowchartRepository.findByUser(user).get(0);
            quarter.setFlowchart(flowchart);

            flowchart.addQuarter(quarter);

            quarterRepository.save(quarter);

            return new ResponseEntity<>(quarter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        if (userRepository.findByEmail(curUser.getUsername()).hasQuarter(id)) {
            quarterRepository.delete(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Quarter> update(@PathVariable Long id, @RequestBody Quarter input, @CurrentUser UserDetails curUser) {
        Quarter quarter = quarterRepository.findOne(id);
        User user = userRepository.findByEmail(curUser.getUsername());

        if (quarter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (user != null && user.hasQuarter(id)) {
            quarter.setQuarter(input.getQuarter());
            quarterRepository.save(quarter);

            return new ResponseEntity<>(quarter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/deleteCourse/{id}")
    public ResponseEntity<Quarter> deleteCourse(@CurrentUser UserDetails curUser, @RequestBody Course input, @PathVariable long id) {
        final User user = userRepository.findByEmail(curUser.getUsername());
        final Quarter quarter = quarterRepository.findOne(id);

        if (user.hasQuarter(id)) {
            Course course = courseRepository.findByName(input.getName());

            quarter.removeCourse(course);
            course.removeQuarter(quarter);

            quarterRepository.save(quarter);

            return new ResponseEntity<>(quarter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/addCourse/{id}")
    public ResponseEntity<Quarter> addCourse(@CurrentUser UserDetails curUser, @RequestBody Course input, @PathVariable long id) {
        final User user = userRepository.findByEmail(curUser.getUsername());
        final Quarter quarter = quarterRepository.findOne(id);

        if (user.hasQuarter(id)) {
            Course course = courseRepository.findByName(input.getName());

            quarter.addCourse(course);
            course.addQuarter(quarter);

            quarterRepository.save(quarter);

            return new ResponseEntity<>(quarter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
