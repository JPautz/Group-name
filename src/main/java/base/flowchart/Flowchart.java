package base.flowchart;

import base.student.Student;
import base.year.Year;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flowchart {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch=FetchType.LAZY)
    private Student student;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "flowchart")
    private List<Year> years;

    public Flowchart() {}

    public Flowchart(String name, Student student) {
        this.name = name;
        this.years = new ArrayList<Year>();
        this.student = student;
        initializeYears();
    }

    public void initializeYears() {
        this.years.add(new Year("Freshman", false, this));
        this.years.add(new Year("Sophomore", false, this));
        this.years.add(new Year("Junior", false, this));
        this.years.add(new Year("Senior", false, this));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public List<Year> getYears() {
        return years;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addYear(Year year) {
        years.add(year);
    }

    public void removeYear(Year year) {
        years.remove(year.getId());
    }
}
