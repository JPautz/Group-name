package base.course;

import base.catalog.Catalog;
import base.catalog.CatalogRepository;
import base.flowchart.Flowchart;
import base.flowchart.FlowchartRepository;
import base.quarter.Quarter;
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
        courseRepository.findAll().forEach(course -> courses.add(course));
        return courses;
    }

    @GetMapping("{id}")
    public Course find(@PathVariable Long id) {
        return courseRepository.findOne(id);
    }

    @PostMapping
    public Course create(@RequestBody Course input) {
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
    public void delete(@PathVariable Long id) {
        courseRepository.delete(id);
    }

    @PutMapping("{id}")
    public Course update(@PathVariable Long id, @RequestBody Course input) {
        Course course = courseRepository.findOne(id);
        if (course == null) {
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
