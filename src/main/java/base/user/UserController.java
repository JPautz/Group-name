package base.user;

import base.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @GetMapping
    public User getCurUser(@CurrentUser UserDetails curUser) {
        User user = userRepository.findByEmail(curUser.getUsername());

        return user;
    }

    @GetMapping("{id}")
    public User find(@PathVariable long id) {
        return userRepository.findOne(id);
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
    public ResponseEntity<User> create(@Valid @RequestBody User reqUser) {
        if (userRepository.findByEmail(reqUser.getEmail()) == null) {
            User user = new User();
            user.setEmail(reqUser.getEmail());
            user.setFirstname(reqUser.getFirstname());
            user.setLastname(reqUser.getLastname());
            user.setPassword(new BCryptPasswordEncoder().encode(reqUser.getPassword()));
            userRepository.save(user);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
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
