package base.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ArrayList<Student> listAll() {
        ArrayList<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(student -> students.add(student));
        return students;
    }

    @GetMapping("{id}")
    public Student find(@PathVariable Long id) {
        return studentRepository.findOne(id);
    }

    @PostMapping
    public Student create(@RequestBody Student input) {
        return studentRepository.save(new Student(input.getForename(), input.getSurname(), input.getEmail(), input.getToken()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        studentRepository.delete(id);
    }

    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student input) {
        Student student = studentRepository.findOne(id);
        if (student == null) {
            return null;
        } else {
            student.setForename(input.getForename());
            student.setSurname(input.getSurname());
            student.setEmail(input.getEmail());
            student.setToken(input.getToken());
            return studentRepository.save(student);
        }
    }
}
