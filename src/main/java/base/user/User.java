package base.user;

import base.flowchart.Flowchart;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users") // 'user' is a keyword in Postgres
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "First name is required.")
    private String firstname;

    @NotEmpty(message = "Last name is required.")
    private String lastname;

    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    @Column(unique=true, nullable = false)
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Flowchart> flowcharts = new ArrayList<Flowchart>();

    public User() {}

    public User(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.flowcharts = user.getFlowcharts();
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

    public List<Flowchart> getFlowcharts() { return flowcharts; }

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

    public void setFlowcharts(List<Flowchart> flowcharts) { this.flowcharts = flowcharts; }

    // Flowchart
    public void addFlowchart(Flowchart flowchart) {
        flowcharts.add(flowchart);
    }

    public void removeFlowchart(Flowchart flowchart) {
        flowcharts.remove(flowchart);
    }
}