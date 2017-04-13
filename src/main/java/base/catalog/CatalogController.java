package base.catalog;

import base.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogRepository catalogRepository;

    @GetMapping
    public Catalog getCatalog() {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        return catalogs.get(0);
    }

    @GetMapping("{name}")
    public Course findCourse(@PathVariable String searchParam) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        return catalogs.get(0).lookUp(searchParam);
    }

    @PostMapping
    public Catalog addCatalog() {
        return catalogRepository.save(new Catalog());
    }

    /*@PostMapping("/newCourse")
    public Course newCourse(@RequestBody Course input) {
        Course course = new Course(input.getPrefix(), input.getNumber(), input.getTitle(),
                input.getUnits(), input.getPrerequisites(), input.getDescription(), input.getTermsOffered());

        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).addCourse(course);
        catalogRepository.save(catalogs.get(0));

        return course;
    }*/

    @PutMapping
    public Catalog addCourse(@RequestBody Course add) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).addCourse(add);
        return catalogRepository.save(catalogs.get(0));
    }

    @PutMapping("{id}")
    public Catalog addCourse(@PathVariable Long courseId) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080").path("/course").queryParam("id", courseId).build().toUri();
        Course c = restTemplate.getForObject(targetUrl, Course.class);
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).addCourse(c);
        return catalogRepository.save(catalogs.get(0));
    }

    @DeleteMapping("{name}")
    public Catalog deleteCourse(@PathVariable String removeParam) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).removeKey(removeParam);
        return catalogRepository.save(catalogs.get(0));
    }


}
