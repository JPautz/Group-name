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
    public ArrayList<Flowchart> listAll() {
        ArrayList<Flowchart> flowcharts = new ArrayList<>();
        flowchartRepository.findAll().forEach(flowchart -> flowcharts.add(flowchart));
        return flowcharts;
    }

    @GetMapping("{id}")
    public Flowchart find(@PathVariable Long id) {
        return flowchartRepository.findOne(id);
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
    public void delete(@PathVariable Long id) {
        flowchartRepository.delete(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Flowchart> update(@PathVariable Long id, @RequestBody Flowchart input) {
        Flowchart flowchart = flowchartRepository.findOne(id);
        if (flowchart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            flowchart.setName(input.getName());
            flowchartRepository.save(flowchart);

            return new ResponseEntity<>(flowchart, HttpStatus.OK);
        }
    }

}
