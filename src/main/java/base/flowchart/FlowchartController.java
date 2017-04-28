package base.flowchart;

import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/flowchart")
public class FlowchartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlowchartRepository flowchartRepository;

    @GetMapping
    public ArrayList<Flowchart> listAll(@CurrentUser UserDetails curUser) {
        ArrayList<Flowchart> flowcharts = new ArrayList<>();
        if (User.isAdmin(curUser)) {
            flowchartRepository.findAll().forEach(flowcharts::add);
        } else {
            flowcharts.add(new Flowchart("error: must be an admin to access"));
        }
        return flowcharts;
    }

    @GetMapping("{id}")
    public Flowchart find(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        User user = userRepository.findByEmail(curUser.getUsername());
        if (user != null && (User.isAdmin(curUser) || user.hasFlowchart(id))) {
            return flowchartRepository.findOne(id);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Flowchart> create(@CurrentUser UserDetails curUser, @RequestBody Flowchart input) {
        User user = userRepository.findByEmail(curUser.getUsername());
        if (user != null) {
            Flowchart flowchart = new Flowchart();
            flowchart.setName(input.getName());
            flowchart.setUser(user);

            user.addFlowchart(flowchart);

            flowchartRepository.save(flowchart);

            return new ResponseEntity<>(flowchart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        User user = userRepository.findByEmail(curUser.getUsername());
        if (user != null && user.hasFlowchart(id)) {
            flowchartRepository.delete(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Flowchart> update(@PathVariable Long id, @RequestBody Flowchart input, @CurrentUser UserDetails curUser) {
        User user = userRepository.findByEmail(curUser.getUsername());
        Flowchart flowchart = flowchartRepository.findOne(id);

        if (flowchart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (user != null && user.hasFlowchart(id)) {
            flowchart.setName(input.getName());
            flowchartRepository.save(flowchart);
            return new ResponseEntity<>(flowchart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
