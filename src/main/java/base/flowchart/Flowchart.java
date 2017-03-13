package base.flowchart;

import base.quarter.Quarter;
import base.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flowchart {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "flowchart")
    private List<Quarter> quarters = new ArrayList<Quarter>();

    public Flowchart() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Quarter> getQuarters() {
        return quarters;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuarters(List<Quarter> quarters) {
        this.quarters = quarters;
    }

    // Quarters
    public void addQuarter(Quarter quarter) {
        quarters.add(quarter);
    }

    public void removeQuarter(Quarter quarter) {
        quarters.remove(quarter);
    }
}
