package base.catalog;

import base.course.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;

@Entity
public class Catalog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private HashMap<String, Course> courseCatalog;

    public Catalog(){
        HashMap<String, Course> hm = new HashMap<String, Course>();
        this.courseCatalog = hm;
    }

    public HashMap<String, Course> getcourseCatalog() {
        return courseCatalog;
    }

    public Course lookUp(String name) {
        return courseCatalog.get(name);
    }

    public void addCourse(Course c) {
        String prefix = c.getPrefix();
        String title = c.getTitle();
        String s = title.concat(" "+ prefix);
        courseCatalog.put(s, c);
    }

    public void removeCourse(Course c) {
        String prefix = c.getPrefix();
        String title = c.getTitle();
        String s = title.concat(" "+ prefix);
        courseCatalog.remove(s);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
