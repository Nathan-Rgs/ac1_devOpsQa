package com.pratica05.game;

import com.pratica05.game.entities.Course;
import com.pratica05.game.entities.Student;
import com.pratica05.game.repositories.CourseRepository;
import com.pratica05.game.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DataJpaTest  // Usado para testes focados no JPA e repositórios
public class CourseRepositoryDataJpaTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student("John Doe");
        student = studentRepository.save(student);
    }

    @Test
    public void testFindByStudentId() {
        Course course1 = new Course();
        course1.setTitle("Java Basics");
        course1.setPoints(100);
        course1.setStudent(student);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Advanced Java");
        course2.setPoints(200);
        course2.setStudent(student);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findByStudentId(student.getId());

        assertNotNull(courses);
        assertEquals(2, courses.size());
    }
}
