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
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "flowchart")
    private List<Quarter> quarters = new ArrayList<Quarter>();

    public Flowchart() {
        for (int curQ = 0; curQ < NUM_Q; ++curQ) {
            Quarter quarter = new Quarter();
            quarter.setQuarter(QuarterName.values()[curQ % (QuarterName.values().length - 1)]);
            quarter.setFlowchart(this);
            quarters.add(quarter);

            if (curQ == 0) {
                Course course = new Course();
                course.setName("CPE308");
                course.setTitle("Software Engineering I");
                course.setUnits(4);
                course.setPrerequisites("CPE/CSC 357; and CSC 141 or CSC 348");
                course.setDescription("Principles for engineering requirements analysis and design of large complex software systems. Software process models. Methods of project planning, tracking, documentation, communication, and quality assurance. Analysis of engineering tradeoffs. Group laboratory project. Technical oral and written presentations. 3 lectures, 1 laboratory.");
                course.setTermsOffered("F, W");
                course.addQuarter(quarter);

                quarter.getCourses().add(course);
            }
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
