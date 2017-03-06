package base.student;

import base.flowchart.Flowchart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String forename;
    private String surname;
    private String email;
    private String token;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private List<Flowchart> flowcharts;

    public Student () {}

    public Student(String forename, String surname, String email, String token) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.token = token;
        this.flowcharts = new ArrayList<Flowchart>();
        initializeFlowcharts();
    }

    public void initializeFlowcharts() {
        for (int i = 0; i < 5; i++) {
            String name = "Flowchart ";
            name += Integer.toString(i + 1);
            this.flowcharts.add(new Flowchart(name, this));
        }
    }

    public Long getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public List<Flowchart> getFlowcharts() { return flowcharts; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}