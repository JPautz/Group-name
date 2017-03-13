package base.flowchart;

import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Flowchart create(@CurrentUser UserDetails curUser, @RequestBody Flowchart input) {
        Flowchart flowchart = new Flowchart();
        flowchart.setName(input.getName());

        User user = userRepository.findByEmail(curUser.getUsername());
        flowchart.setUser(user);

        user.addFlowchart(flowchart);

        return flowchartRepository.save(flowchart);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        flowchartRepository.delete(id);
    }

    @PutMapping("{id}")
    public Flowchart update(@PathVariable Long id, @RequestBody Flowchart input) {
        Flowchart flowchart = flowchartRepository.findOne(id);
        if(flowchart == null) {
            return null;
        }
        else {
            flowchart.setName(input.getName());
            return flowchartRepository.save(flowchart);
        }
    }

}
