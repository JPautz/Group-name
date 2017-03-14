package base.flowchart;

import base.course.Course;
import base.quarter.Quarter;
import base.quarter.QuarterName;
import base.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flowchart {

    private static final int NUM_Q = 12;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "flowchart")
    private List<Quarter> quarters = new ArrayList<Quarter>();

    public Flowchart() {
        for (int curQ = 0; curQ < NUM_Q; ++curQ) {
            Quarter quarter = new Quarter();
            quarter.setQuarter(QuarterName.values()[curQ % (QuarterName.values().length - 1)]);
            quarter.setFlowchart(this);
            quarters.add(quarter);
        }
    }

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

    public boolean ownsQuarter(Quarter quarter) {
        for (Quarter q : quarters) {
            if (q.getId() == quarter.getId()) {
                return true;
            }
        }

        return false;
    }
}
