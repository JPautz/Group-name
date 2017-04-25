package base.user;

import base.flowchart.Flowchart;
import base.year.Year;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // 'user' is a keyword in Postgres
public class User implements Serializable {

    private static final String TEST_ADMIN = "admin@admin.com";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "First name is required.")
    private String firstname;

    @NotEmpty(message = "Last name is required.")
    private String lastname;

    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Flowchart> flowcharts = new ArrayList<Flowchart>();

    public User() {
        Flowchart flowchart = new Flowchart();
        flowchart.setName("Flowchart 1");
        flowchart.setUser(this);
        flowcharts.add(flowchart);
    }

    public User(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.flowcharts = user.getFlowcharts();
    }

    public static boolean isAdmin(UserDetails curUser) {
        return curUser.getAuthorities().contains(new SimpleGrantedAuthority
                ("ROLE_ADMIN")) || curUser.getUsername().equals(TEST_ADMIN);
    }

    public Long getId() {
        return id;
    }

    // Getters
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Flowchart> getFlowcharts() {
        return flowcharts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFlowcharts(List<Flowchart> flowcharts) {
        this.flowcharts = flowcharts;
    }

    // Flowchart
    public void addFlowchart(Flowchart flowchart) {
        flowcharts.add(flowchart);
    }

    public void removeFlowchart(Flowchart flowchart) {
        flowcharts.remove(flowchart);
    }

    public boolean hasFlowchart(long id) {
        for (Flowchart flowchart : flowcharts) {
            if (flowchart.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasYear(long  id) {
        for (Flowchart flowchart : flowcharts) {
            if(flowchart.hasYear(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasQuarter(long id) {
        for (Flowchart flowchart : flowcharts) {
            for(Year year: flowchart.getYears()) {
                if (year.hasQuarter(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}