package base.year;

import base.quarter.Quarter;

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
    private String date;
    private boolean showSummer;
    private ArrayList<Quarter> quarters;

    public Year() {}

    public Year(String date, boolean showSummer) {
        this.date = date;
        this.showSummer = showSummer;
        this.quarters = new ArrayList<Quarter>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() { return date; }

    public boolean getShowSummer() { return showSummer; }

    public void setDate(String date) { this.date = date; }

    public void toggleSummer() {
        this.showSummer =  !this.showSummer;
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
