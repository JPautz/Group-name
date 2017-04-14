package base;

import base.course.Course;
import base.course.CourseController;
import base.course.CourseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.util.ArrayList;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        loadCourses(ctx);
    }

    private static void loadCourses(ApplicationContext ctx) {
        CourseRepository cr = (CourseRepository) ctx.getBean("courseRepository");

        CatalogParser cp = new CatalogParser();
        ArrayList<String> departments = cp.getDepartments();

        for (String department : departments) {
            ArrayList<Course> courses = cp.getCourses(department);
            cr.save(courses);
        }
    }

}
