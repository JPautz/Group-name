package base.year;

import base.quarter.Quarter;
import base.quarter.QuarterName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Year {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean showSummer;
    private ArrayList<Quarter> quarters;

    public Year() {}

    public Year(String name, boolean showSummer) {
        this.name = name;
        this.showSummer = showSummer;
        this.quarters = new ArrayList<Quarter>();
        initializeQuarters();
    }

    public void initializeQuarters() {
        this.quarters.add(new Quarter(QuarterName.FALL));
        this.quarters.add(new Quarter(QuarterName.WINTER));
        this.quarters.add(new Quarter(QuarterName.SPRING));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public boolean getShowSummer() { return showSummer; }

    public void setName(String date) { this.name = name; }

    public void setShowSummer(boolean showSummer) {
        this.showSummer =  showSummer;
    }

    public ArrayList<Quarter> getQuarters() {
        return quarters;
    }

    public void addQuarter(Quarter quarter) {
        quarters.add(quarter);
    }

    public void removeQuarter(Quarter quarter) {
        quarters.remove(quarter.getId());
    }
}
