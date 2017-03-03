package base.year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Year {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String date;
    private boolean showSummer;

    public Year() {}

    public Year(String date, boolean showSummer) {
        this.date = date;
        this.showSummer = showSummer;
    }

    public String getDate() { return date; }

    public boolean getShowSummer() { return showSummer; }

    public void setDate(String date) { this.date = date; }

    public void toggleSummer() {
        this.showSummer =  !this.showSummer;
    }
}
