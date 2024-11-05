package com.pratica05.game;

import com.pratica05.game.entities.Course;
import com.pratica05.game.entities.Student;
import com.pratica05.game.repositories.CourseRepository;
import com.pratica05.game.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student("Pedro Silva");
        student = studentRepository.save(student);
    }

    @Test
    public void testSaveCourse() {
        Course course = new Course();
        course.setTitle("Curso de Java");
        course.setPoints(100);
        course.setStudent(student);

        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse);
        assertNotNull(savedCourse.getId());
        assertEquals("Curso de Java", savedCourse.getTitle());
        assertEquals(100, savedCourse.getPoints());
        assertEquals(student.getId(), savedCourse.getStudent().getId());
    }

    @Test
    public void testFindByStudentId() {
        Course course1 = new Course();
        course1.setTitle("Curso de Java");
        course1.setPoints(100);
        course1.setStudent(student);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Curso de Java Avançado");
        course2.setPoints(200);
        course2.setStudent(student);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findByStudentId(student.getId());

        assertNotNull(courses);
        assertEquals(2, courses.size());
        assertTrue(courses.stream().anyMatch(course -> course.getTitle().equals("Curso de Java")));
        assertTrue(courses.stream().anyMatch(course -> course.getTitle().equals("Curso de Java Avançado")));
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course();
        course.setTitle("Curso de Java");
        course.setPoints(100);
        course.setStudent(student);
        Course savedCourse = courseRepository.save(course);

        courseRepository.delete(savedCourse);

        assertFalse(courseRepository.findById(savedCourse.getId()).isPresent());
    }
}
