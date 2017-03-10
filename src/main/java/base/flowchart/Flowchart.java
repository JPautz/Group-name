package base.flowchart;

import base.user.User;
import base.year.Year;

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
    private User user;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "flowchart")
    private List<Year> years;

    public Flowchart() {}

    public Flowchart(String name, User user) {
        this.name = name;
        this.years = new ArrayList<Year>();
        this.user = user;
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

    public void setUser(User user) {
        this.user = user;
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

    public User getUser() { return this.user; }
}
