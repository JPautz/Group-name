package base.course;

import base.quarter.Quarter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String title;

    private String units;
    private String prerequisites;
    private String description;
    private String termsOffered;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Quarter> quarters;

    public Course() {
        quarters = new ArrayList<>();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUnits() {
        return units;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public String getDescription() {
        return description;
    }

    public String getTermsOffered() {
        return termsOffered;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTermsOffered(String termsOffered) {
        this.termsOffered = termsOffered;
    }

    // Quarters
    public void addQuarter(Quarter quarter) {
        quarters.add(quarter);
    }

    public void removeQuarter(Quarter quarter) {
        quarters.remove(quarter);
    }
}
