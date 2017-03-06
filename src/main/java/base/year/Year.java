package base.year;

import base.flowchart.Flowchart;
import base.quarter.Quarter;
import base.quarter.QuarterName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Year {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean showSummer;
    @ManyToOne(fetch=FetchType.LAZY)
    private Flowchart flowchart;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "year")
    private List<Quarter> quarters;

    public Year() {}

    public Year(String name, boolean showSummer, Flowchart flowchart) {
        this.name = name;
        this.showSummer = showSummer;
        this.quarters = new ArrayList<Quarter>();
        this.flowchart = flowchart;
        initializeQuarters();
    }

    public void initializeQuarters() {
        this.quarters.add(new Quarter(QuarterName.FALL, this));
        this.quarters.add(new Quarter(QuarterName.WINTER, this));
        this.quarters.add(new Quarter(QuarterName.SPRING, this));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlowchart(Flowchart flowchart) {
        this.flowchart = flowchart;
    }

    public String getName() { return name; }

    public boolean getShowSummer() { return showSummer; }

    public void setName(String date) { this.name = name; }

    public void setShowSummer(boolean showSummer) {
        this.showSummer =  showSummer;
    }

    public List<Quarter> getQuarters() {
        return quarters;
    }

    public void addQuarter(Quarter quarter) {
        quarters.add(quarter);
    }

    public void removeQuarter(Quarter quarter) {
        quarters.remove(quarter.getId());
    }
}
