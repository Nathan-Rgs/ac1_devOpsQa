package com.pratica05.game.controller;

import com.pratica05.game.entities.Course;
import com.pratica05.game.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    // Outros métodos para manipulação de cursos (exemplo de busca por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    // Endpoint para adicionar um novo curso
    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    // Endpoint para listar cursos por ID do estudante
    @GetMapping("/studentCourses")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@RequestParam Long studentId) {
        List<Course> courses = courseService.findCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }

    // Endpoint para verificar se um estudante é elegível para Premium
    @GetMapping("/isEligibleForPremium")
    public ResponseEntity<Boolean> isEligibleForPremium(@RequestParam int totalCourses) {
        boolean eligible = courseService.isEligibleForPremium(totalCourses);
        return ResponseEntity.ok(eligible);
    }
}
