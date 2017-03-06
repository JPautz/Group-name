package base.quarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/quarter")
public class QuarterController {

    @Autowired
    private QuarterRepository quarterRepository;

    @GetMapping
    public ArrayList<Quarter> listAll() {
        ArrayList<Quarter> quarters = new ArrayList<>();
        quarterRepository.findAll().forEach(quarter -> quarters.add(quarter));
        return quarters;
    }

    @GetMapping("{id}")
    public Quarter find(@PathVariable Long id) {
        return quarterRepository.findOne(id);
    }

    /*@PostMapping
    public Quarter create(@RequestBody Quarter input) {
        return quarterRepository.save(new Quarter(input.getQuarter()));
    }*/

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        quarterRepository.delete(id);
    }

    @PutMapping("{id}")
    public Quarter update(@PathVariable Long id, @RequestBody Quarter input) {
        Quarter quarter = quarterRepository.findOne(id);
        if (quarter == null) {
            return null;
        } else {
            quarter.setQuarter(input.getQuarter());
            return quarterRepository.save(quarter);
        }
    }

}
