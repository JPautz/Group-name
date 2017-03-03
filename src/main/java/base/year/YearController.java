package base.year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/year")
public class YearController {

    @Autowired
    private YearRepository yearRepository;

    @GetMapping
    public ArrayList<Year> listAll() {
        ArrayList<Year> years = new ArrayList<>();
        yearRepository.findAll().forEach(year -> years.add(year));
        return years;
    }

    @GetMapping("{id}")
    public Year find(@PathVariable Long id) { return yearRepository.findOne(id); }

    @PostMapping
    public Year create(@RequestBody Year input) {
        return yearRepository.save(new Year(input.getDate(), input.getShowSummer()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) { yearRepository.delete(id); }

    @PutMapping("{id}")
    public Year update(@PathVariable Long id, @RequestBody Year input) {
        Year year = yearRepository.findOne(id);
        if (year == null) {
            return null;
        } else {
            year.setDate(input.getDate());
            year.setShowSummer(input.getShowSummer());
            return yearRepository.save(year);
        }
    }
}
