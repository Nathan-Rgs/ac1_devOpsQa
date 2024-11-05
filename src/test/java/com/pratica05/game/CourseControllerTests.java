package com.pratica05.game;

import com.pratica05.game.controller.CourseController;
import com.pratica05.game.entities.Course;
import com.pratica05.game.interfaces.CourseControllerInterface;
import com.pratica05.game.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseControllerTests implements CourseControllerInterface {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Override
    @Test
    public void createCourse_ShouldReturnSavedCourse() {
        Course course = new Course(1L, "Java Basics", 100, null);
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        ResponseEntity<Course> response = courseController.createCourse(course);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).saveCourse(any(Course.class));
    }

    @Override
    @Test
    public void getCourseById_ShouldReturnCourse_WhenCourseExists() {
        Course course = new Course(1L, "Java Basics", 100, null);
        when(courseService.getCourseById(1L)).thenReturn(course);

        ResponseEntity<Course> response = courseController.getCourseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).getCourseById(1L);
    }

    @Override
    @Test
    public void getCourseById_ShouldThrowException_WhenCourseNotFound() {
        when(courseService.getCourseById(1L)).thenThrow(new RuntimeException("Course not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            courseController.getCourseById(1L);
        });

        assertEquals("Course not found", exception.getMessage());
        verify(courseService, times(1)).getCourseById(1L);
    }

    @Override
    @Test
    public void addCourse_ShouldReturnSavedCourse() {
        Course course = new Course(1L, "Java Basics", 100, null);
        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        ResponseEntity<Course> response = courseController.addCourse(course);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).saveCourse(any(Course.class));
    }

    @Override
    @Test
    public void getCoursesByStudentId_ShouldReturnListOfCourses() {
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java Basics", 100, null),
                new Course(2L, "Advanced Java", 150, null)
        );
        when(courseService.findCoursesByStudentId(1L)).thenReturn(courses);

        ResponseEntity<List<Course>> response = courseController.getCoursesByStudentId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).findCoursesByStudentId(1L);
    }

    @Override
    @Test
    public void isEligibleForPremium_ShouldReturnEligibilityStatus() {
        when(courseService.isEligibleForPremium(5)).thenReturn(true);

        ResponseEntity<Boolean> response = courseController.isEligibleForPremium(5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(courseService, times(1)).isEligibleForPremium(5);
    }

    @Override
    @Test
    public void isEligibleForPremium_ShouldReturnFalse_WhenNotEligible() {
        when(courseService.isEligibleForPremium(3)).thenReturn(false);

        ResponseEntity<Boolean> response = courseController.isEligibleForPremium(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(courseService, times(1)).isEligibleForPremium(3);
    }
}