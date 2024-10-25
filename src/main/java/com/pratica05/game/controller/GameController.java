package com.pratica05.game.controller;

import com.pratica05.game.entities.Student;  // Atualizando o pacote para 'model'
import com.pratica05.game.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final StudentService studentService;

    public GameController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/participate")
    public String participateInForum(@RequestParam Long studentId, @RequestParam String comment) {
        // Buscar o estudante pelo ID
        Student student = studentService.getStudentById(studentId);

        // Executar a participação no fórum
        studentService.participateInForum(studentId, comment);

        // Retornar o feedback com sugestão, se aplicável
        if (student.isActive()) {
            return "Feedback: " + student.getFeedback() + " Suggestion: " + student.getSuggestion();
        } else {
            return "Feedback: " + student.getFeedback();
        }
    }

    @PostMapping("/upgrade")
    public String upgradeToPremium(@RequestParam Long studentId) {
        // Buscar o estudante pelo ID
        Student student = studentService.getStudentById(studentId);

        // Promover o estudante a Premium
        studentService.upgradeToPremium(studentId);

        // Retornar a mensagem de sucesso ou falha
        if (student.isPremium()) {
            return "Student " + student.getName() + " is now Premium!";
        }
        return "Student " + student.getName() + " has not yet qualified for Premium.";
    }

    @PostMapping("/achievements")
    public ResponseEntity<String> calculateAchievements(@RequestParam Long studentId, @RequestParam int points) {
        try {
            // Chama o serviço para calcular as conquistas
            studentService.calculateAchievements(studentId, points);
            return ResponseEntity.ok("Achievements updated successfully.");
        } catch (RuntimeException e) {
            // Retorna erro se o estudante não for encontrado
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
