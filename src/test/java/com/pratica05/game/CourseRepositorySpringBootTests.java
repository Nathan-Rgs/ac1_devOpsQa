package com.pratica05.game;

import com.pratica05.game.entities.Course;
import com.pratica05.game.entities.Student;
import com.pratica05.game.repositories.CourseRepository;
import com.pratica05.game.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest  // Carrega o contexto completo da aplicação
@ActiveProfiles("test")  // Usa um perfil de teste para evitar impacto no ambiente de produção
@Transactional  // Garante rollback automático após cada teste, útil para testes de integração
public class CourseRepositorySpringBootTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student("Jane Doe");
        student = studentRepository.save(student);
    }

    @Test
    public void testSaveAndRetrieveCourse() {
        Course course = new Course();
        course.setTitle("Spring Boot Basics");
        course.setPoints(150);
        course.setStudent(student);
        courseRepository.save(course);

        List<Course> courses = courseRepository.findByStudentId(student.getId());

        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals("Spring Boot Basics", courses.get(0).getTitle());
    }

    @Test
    public void testFindAllCourses() {
        Course course1 = new Course();
        course1.setTitle("Spring Boot Basics");
        course1.setPoints(150);
        course1.setStudent(student);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setTitle("Advanced Spring Boot");
        course2.setPoints(250);
        course2.setStudent(student);
        courseRepository.save(course2);

        List<Course> allCourses = courseRepository.findAll();

        assertNotNull(allCourses);
        assertEquals(2, allCourses.size());
    }
}
