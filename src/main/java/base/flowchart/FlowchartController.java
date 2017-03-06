package base.flowchart;

import base.year.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/flowchart")
public class FlowchartController {

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

    /*@PostMapping
    public Flowchart create(@RequestBody Flowchart input) {
        return flowchartRepository.save(new Flowchart(input.getName()));
    }*/

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        flowchartRepository.delete(id);
    }

    @PutMapping("{id}")
    public Flowchart update(@PathVariable Long id, @RequestBody Flowchart input) {
        Flowchart flowchart = flowchartRepository.findOne(id);
        ArrayList<Year> years;
        if(flowchart == null) {
            return null;
        }
        else {
            flowchart.setName(input.getName());
            for(int i = 0; i < flowchart.getYears().size(); i++) {
                flowchart.removeYear(flowchart.getYears().get(i));
            }
            for(int i = 0; i < input.getYears().size(); i++) {
                flowchart.addYear(input.getYears().get(i));
            }
            return flowchartRepository.save(flowchart);
        }
    }

}
