package base.quarter;

import base.course.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Quarter {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private QuarterName quarter;
    private ArrayList<Course> courses;

    public Quarter(QuarterName quarter) {
        this.quarter = quarter;
        this.courses = new ArrayList<>();
        initializeCourses();
    }

    public void initializeCourses() {}


    public Long getId() {
        return id;
    }

    public QuarterName getQuarter() {
        return quarter;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course.getId());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuarter(QuarterName quarter) {
        this.quarter = quarter;
    }
}
