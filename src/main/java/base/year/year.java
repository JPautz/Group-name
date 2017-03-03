package base.year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gcloonan on 3/2/17.
 */
public class year {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String date;
    private boolean showSummer;

    public year() {}

    public year(String date, boolean showSummer) {
        this.date = date;
        this.showSummer = showSummer;
    }

    public String getDate() { return date; }

    public boolean getShowSummer() { return showSummer; }

    public void setDate(String date) { this.date = date; }

    public void toggleSummer() {
        this.showSummer != this.showSummer;
    }
}
