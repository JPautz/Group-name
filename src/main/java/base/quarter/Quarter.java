package base.quarter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quarter {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private QuarterName quarter;
    private ArrayList<Course> courses;

    public Quarter (){};

    public Quarter(QuarterName quarter) {
        this.quarter = quarter;
    }

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
