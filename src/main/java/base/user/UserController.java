package base.user;

import base.course.Course;
import base.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private UserRepository userRepository;

    // Return only logged in user
    @GetMapping
    public UserDetails getCurrentUser(@CurrentUser UserDetails currentUser) {
        return currentUser;
    }

    @RequestMapping("/all")
    public List<User> getUsers(@CurrentUser UserDetails currentUser) {
        ArrayList<User> users = new ArrayList<>();
        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            userRepository.findAll().forEach(users::add);
        }
        return users;
    }

    @PostMapping
    public User create(@Valid @RequestBody User reqUser) {
        User user = new User();
        user.setEmail(reqUser.getEmail());
        user.setFirstname(reqUser.getFirstname());
        user.setLastname(reqUser.getLastname());
        user.setPassword(new BCryptPasswordEncoder().encode(reqUser.getPassword()));
        return userRepository.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        // ADMIN Route
        userRepository.delete(id);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User reqUser) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return null;
        } else {
            user.setEmail(reqUser.getEmail());
            user.setFirstname(reqUser.getFirstname());
            user.setLastname(reqUser.getLastname());
            user.setEmail(reqUser.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(reqUser.getPassword()));
            return userRepository.save(user);
        }
    }
}
