package com.pratica05.game.service;

import com.pratica05.game.entities.Course;
import com.pratica05.game.entities.Student;
import com.pratica05.game.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    // Método para salvar um novo curso
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // Método para listar cursos de um estudante específico
    public List<Course> findCoursesByStudentId(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    // Método para calcular a pontuação total dos cursos de um estudante
    public int calculateTotalCoursePoints(Long studentId) {
        return courseRepository.findByStudentId(studentId)
                .stream()
                .mapToInt(Course::getPoints)
                .sum();
    }

    public boolean isEligibleForPremium(int totalCourses) {
        int requiredCoursesForPremium = 12;
        return totalCourses >= requiredCoursesForPremium;
    }
}
