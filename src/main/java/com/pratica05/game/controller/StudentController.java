package com.pratica05.game.controller;

import com.pratica05.game.dto.CreateStudentDto;
import com.pratica05.game.entities.Student;
import com.pratica05.game.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentDto studentDTO) {
        Student savedStudent = studentService.saveStudent(studentDTO);
        return ResponseEntity.ok(savedStudent);
    }

    // Outros métodos para manipulação de estudantes (exemplo de busca por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // Endpoint para promover estudante a Premium
    @PostMapping("/upgradeToPremium")
    public ResponseEntity<String> upgradeToPremium(@RequestParam Long studentId) {
        try {
            studentService.upgradeToPremium(studentId);
            return ResponseEntity.ok("Student upgraded to Premium successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para adicionar moedas
    @PostMapping("/addCoins")
    public ResponseEntity<String> addCoins(@RequestParam Long studentId, @RequestParam int coins) {
        try {
            studentService.addCoins(studentId, coins);
            return ResponseEntity.ok("Coins added successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para usar moedas
    @PostMapping("/useCoins")
    public ResponseEntity<String> useCoins(@RequestParam Long studentId, @RequestParam int numberOfCoins) {
        try {
            studentService.useCoins(studentId, numberOfCoins);
            return ResponseEntity.ok("Coins used successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para calcular conquistas
    @PostMapping("/calculateAchievements")
    public ResponseEntity<String> calculateAchievements(@RequestParam Long studentId, @RequestParam int points) {
        try {
            studentService.calculateAchievements(studentId, points);
            return ResponseEntity.ok("Achievements calculated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para participação no fórum
    @PostMapping("/participateInForum")
    public ResponseEntity<String> participateInForum(@RequestParam Long studentId, @RequestParam String comment) {
        try {
            studentService.participateInForum(studentId, comment);
            return ResponseEntity.ok("Participation registered successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Endpoint para sugerir cursos
    @GetMapping("/suggestCourses")
    public ResponseEntity<String> suggestCourses(@RequestParam Long studentId) {
        try {
            String suggestion = studentService.suggestCourses(studentId);
            return ResponseEntity.ok(suggestion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
