package base.student;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Student, Long> {
}
