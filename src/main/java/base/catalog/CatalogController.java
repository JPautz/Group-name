package base.catalog;

import base.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public Catalog addCourse(@RequestBody Course add) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).addCourse(add);
        return catalogRepository.save(catalogs.get(0));
    }
    /*
    @PutMapping("{id}")
    public Catalog addCourse(@PathVariable Long courseId) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Course c = restTemplate.getForObject("http://localhost:8080/course", Course.class, )
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).addCourse(add);
        return catalogRepository.save(catalogs.get(0));
    }*/

    @DeleteMapping("{name}")
    public Catalog deleteCourse(@PathVariable String removeParam) {
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalog -> catalogs.add(catalog));
        catalogs.get(0).removeKey(removeParam);
        return catalogRepository.save(catalogs.get(0));
    }




}
