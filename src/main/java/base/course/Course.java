package base.course;

import base.quarter.Quarter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String prefix;
    private String number;
    private String title;

    private int units;
    private String prerequisites;
    private String description;
    private String termsOffered;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    private List<Quarter> quarters;

    public Course() {}

    public Course(String prefix, String number, String title, int units, String prerequisites, String description,
                  String termsOffered, Quarter quarter) {
        this.prefix = prefix;
        this.number = number;
        this.title = title;
        this.description = description;
        this.units = units;
        this.prerequisites = prerequisites;
        this.termsOffered = termsOffered;
        this.quarters.add(quarter);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOffered() {
        return termsOffered;
    }

    public void setTermsOffered(String termsOffered) {
        this.termsOffered = termsOffered;
    }
}
