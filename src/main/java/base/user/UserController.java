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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(UserDetails curUser) {
        return curUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @GetMapping
    public ArrayList<User> getCurUser(@CurrentUser UserDetails curUser) {
        ArrayList<User> users = new ArrayList<>();
        if (isAdmin(curUser)) {
            userRepository.findAll().forEach(users::add);
        } else {
            users.add(userRepository.findByEmail(curUser.getUsername()));
        }
        return users;
    }

    @GetMapping("{id}")
    public User find(@PathVariable long id/*, @CurrentUser UserDetails curUser*/) {
        User reqUser = userRepository.findOne(id);
        return reqUser;
        /*if (isAdmin(curUser)) {
            return reqUser;
        } else if (reqUser.getEmail().equals(curUser.getUsername())) {
            return reqUser;
        }
        return null;*/
    }

    //method is redundant
    /*@RequestMapping("/all")
    public List<User> getUsers(@CurrentUser UserDetails currentUser) {
        ArrayList<User> users = new ArrayList<>();
        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            userRepository.findAll().forEach(users::add);
        }
        return users;
    }*/

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User reqUser) {
        if (userRepository.findByEmail(reqUser.getEmail()) == null) {
            // create user
            User user = new User();
            user.setEmail(reqUser.getEmail());
            user.setFirstname(reqUser.getFirstname());
            user.setLastname(reqUser.getLastname());
            user.setPassword(new BCryptPasswordEncoder().encode(reqUser.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            // email already taken
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        if (isAdmin(curUser))
            userRepository.delete(id);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser, @CurrentUser UserDetails curUser) {
        User reqUser = userRepository.findOne(id);
        if (reqUser == null) {
            //user not found
            return null;
        } else if (reqUser.getEmail().equals(curUser.getUsername())) {
            reqUser.setEmail(newUser.getEmail());
            reqUser.setFirstname(newUser.getFirstname());
            reqUser.setLastname(newUser.getLastname());
            reqUser.setEmail(newUser.getEmail());
            reqUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
            User x = userRepository.save(reqUser);
            System.out.println(curUser.getUsername());
            return x;
        }
        return null;
    }
}

