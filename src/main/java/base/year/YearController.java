package base.year;

import base.flowchart.Flowchart;
import base.flowchart.FlowchartRepository;
import base.security.CurrentUser;
import base.user.User;
import base.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/year")
public class YearController {

  @Autowired
  private YearRepository yearRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FlowchartRepository flowchartRepository;

  @GetMapping
  public List<Year> listAll(@CurrentUser UserDetails curUser) {
    ArrayList<Year> years = new ArrayList<>();
    if (User.isAdmin(curUser)) {
      yearRepository.findAll().forEach(year -> years.add(year));
    } else {
      //years.add(new Year("error: must be an admin to access"));
      return Collections.emptyList();
    }
    return years;
  }
  @GetMapping("{id}")
  public Year find(@PathVariable Long id, @CurrentUser UserDetails curUser) {
    User user = userRepository.findByEmail(curUser.getUsername());
    if (user != null && (User.isAdmin(curUser) || user.hasYear(id))) {
      return yearRepository.findOne(id);
    }
    return null;
  }

  @PostMapping
  public ResponseEntity<Year> create(@CurrentUser UserDetails curUser, @RequestBody Year input) {
    User user = userRepository.findByEmail(curUser.getUsername());
    if (user != null) {
      Year year = new Year();
      year.setName(input.getName());
      year.setShowSummer(input.getShowSummer());
      year.setFlowchart(input.getFlowchart());
      Flowchart flowchart = flowchartRepository.findOne(input.getFlowchart().getId());
      flowchart.addYear(year);

      yearRepository.save(year);

      return new ResponseEntity<>(year, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id, @CurrentUser UserDetails curUser) {
    User user = userRepository.findByEmail(curUser.getUsername());
    if (user != null && user.hasFlowchart(id)) {
      yearRepository.delete(id);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Year> update(@PathVariable Long id, @RequestBody Year input, @CurrentUser UserDetails curUser) {
    User user = userRepository.findByEmail(curUser.getUsername());
    Year year = yearRepository.findOne(id);

    if (year == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (user != null && user.hasYear(id)) {
      year.setName(input.getName());
      year.setShowSummer(input.getShowSummer());
      year.setFlowchart(input.getFlowchart());
      yearRepository.save(year);
      return new ResponseEntity<>(year, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }
}