package base.flowchart;

import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.SOAPPart;
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
        if (curUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            flowchartRepository.findAll().forEach(flowchart -> flowcharts.add(flowchart));
        } else {
            flowchartRepository.findAll().forEach(flowchart -> flowcharts.add(flowchart));
            //flowcharts.add(new Flowchart("error: must be an admin to access"));
        }
        return flowcharts;
    }

    @GetMapping("{id}")
    public Flowchart find(@PathVariable Long id, @CurrentUser UserDetails curUser) {
        User user = userRepository.findByEmail(curUser.getUsername());
        System.out.println("............................" + curUser.getUsername());
        if (user != null) {
            return flowchartRepository.findOne(id);
        }
        return null;
    }

    @PostMapping
    public Flowchart create(@CurrentUser UserDetails curUser, @RequestBody Flowchart input) {
        User user = userRepository.findByEmail(curUser.getUsername());
        if (user != null) {
            Flowchart flowchart = new Flowchart();
            flowchart.setName(input.getName());

            flowchart.setUser(user);

            user.addFlowchart(flowchart);

            return flowchartRepository.save(flowchart);
        } else {
            return null;
        }
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
