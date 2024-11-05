package com.pratica05.game;

import com.pratica05.game.entities.Course;
import com.pratica05.game.entities.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CourseEntityTests {

    @Test
    public void testCourseCreation() {
        Course course = new Course();
        course.setTitle("Curso de Java");
        course.setPoints(100);

        assertNotNull(course);
        assertEquals("Curso de Java", course.getTitle());
        assertEquals(100, course.getPoints());
    }

    @Test
    public void testCourseStudentRelationship() {
        Student student = new Student("Pedro Silva");
        Course course = new Course();
        course.setTitle("Curso de Java");
        course.setPoints(100);
        course.setStudent(student);

        assertEquals(student, course.getStudent());
        assertEquals("Pedro Silva", course.getStudent().getName());
    }

}
