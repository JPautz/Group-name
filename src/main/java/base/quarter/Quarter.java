package base.quarter;

import base.course.Course;
import base.flowchart.Flowchart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quarter {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private QuarterName quarter;
    @ManyToOne(fetch=FetchType.EAGER)
    private Flowchart flowchart;
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "quarters")
    private List<Course> courses = new ArrayList<Course>();

    public Quarter() {}

    // Getters
    public Long getId() {
        return id;
    }

    public QuarterName getQuarter() {
        return quarter;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setQuarter(QuarterName quarter) {
        this.quarter = quarter;
    }

    public void setFlowchart(Flowchart flowchart) {
        this.flowchart = flowchart;
    }

    // Courses
    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
