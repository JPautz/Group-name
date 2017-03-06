package base.flowchart;

import base.year.Year;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Flowchart {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private ArrayList<Year> years;

    public Flowchart(String name) {
        this.name = name;
        this.years = new ArrayList<Year>();
        initializeYears();
    }

    public void initializeYears() {
        this.years.add(new Year("Freshman", false));
        this.years.add(new Year("Sophomore", false));
        this.years.add(new Year("Junior", false));
        this.years.add(new Year("Senior", false));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Year> getYears() {
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
