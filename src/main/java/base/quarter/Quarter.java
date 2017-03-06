package base.quarter;

import base.course.Course;
import base.year.Year;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quarter {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private QuarterName quarter;
    @ManyToOne(fetch=FetchType.LAZY)
    private Year year;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quarters")
    private List<Course> courses;

    public Quarter() {}

    public Quarter(QuarterName quarter, Year year) {
        this.quarter = quarter;
        this.courses = new ArrayList<Course>();
        this.year = year;
        initializeCourses();
    }

    public void initializeCourses() {}


    public Long getId() {
        return id;
    }

    public QuarterName getQuarter() {
        return quarter;
    }

    public List<Course> getCourses() {
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

    public void setYear(Year year) {
        this.year = year;
    }
}
