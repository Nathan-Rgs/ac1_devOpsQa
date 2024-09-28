package com.pratica05.game.controller;

import com.pratica05.game.model.Student;
import com.pratica05.game.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class GameController {

    private final StudentService studentService;

    public GameController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/participate")
    public String participateInForum(@RequestParam String name, @RequestParam String comment) {
        Student student = new Student(name);
        studentService.participateInForum(student, comment);
        if (student.isActive()) {
            return "Feedback: " + student.getFeedback() + ". Suggestion" + student.getSuggestion();
        } else {
            return "Feedback: " + student.getFeedback();
        }
    }

    @PostMapping("/upgrade")
    public String upgradeToPremium(@RequestParam String name) {
        Student student = new Student(name);
        studentService.upgradeToPremium(student);
        if (student.isPremium()) {
            return "Student " + student.getName() + " is now Premium!";
        }
        return "Student " + student.getName() + " has not yet qualified for Premium.";
    }
}
