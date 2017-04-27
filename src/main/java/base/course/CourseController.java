package base.course;

import base.flowchart.FlowchartRepository;
import base.quarter.QuarterRepository;
import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlowchartRepository flowchartRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ArrayList<Course> listAll() {
        ArrayList<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    @PostMapping("search")
    public Course findCourse(@RequestBody Course input) {
        return courseRepository.findByName(input.getName());
    }

    @GetMapping("{id}")
    public Course find(@PathVariable Long id) {
        return courseRepository.findOne(id);
    }

    @PostMapping
    public Course create(@RequestBody Course input, @CurrentUser UserDetails curUser) {
        if (!User.isAdmin(curUser)) {
            return null;
        }

        Course course = new Course();
        course.setName(input.getName());
        course.setTitle(input.getTitle());
        course.setUnits(input.getUnits());
        course.setPrerequisites(input.getPrerequisites());
        course.setDescription(input.getDescription());
        course.setTermsOffered(input.getTermsOffered());

        return courseRepository.save(course);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        if (User.isAdmin(curUser)) {
            courseRepository.delete(id);
        }
    }

    @PutMapping("{id}")
    public Course update(@PathVariable Long id, @RequestBody Course input, @CurrentUser UserDetails curUser) {
        Course course = courseRepository.findOne(id);

        if (course == null || !User.isAdmin(curUser)) {
            return null;
        } else {
            course.setName(input.getName());
            course.setTitle(input.getTitle());
            course.setUnits(input.getUnits());
            course.setPrerequisites(input.getPrerequisites());
            course.setDescription(input.getDescription());
            course.setTermsOffered(input.getTermsOffered());
            return courseRepository.save(course);
        }
    }

}
