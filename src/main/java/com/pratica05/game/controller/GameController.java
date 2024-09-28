package com.pratica05.game.controller;

import com.pratica05.game.model.Student;
import com.pratica05.game.service.StudentService;
import org.springframework.web.bind.annotation.*;
import com.pratica05.game.interfaces.controller.ControllerInterface;
import com.pratica05.game.factory.StudentFactory;

@RestController
@RequestMapping("/student")
public class GameController implements ControllerInterface {
    private final StudentService studentService;
    private final StudentFactory studentFactory;

    public GameController(StudentService studentService) {
        this.studentService = studentService;
        this.studentFactory = new StudentFactory();
    }

    @PostMapping("/participate")
    public String participateInForum(@RequestParam String name, @RequestParam String comment) {
        Student student = this.studentFactory.get_student(name);
        studentService.participateInForum(student, comment);
        if (student.isActive()) {
            return "Feedback: " + student.getFeedback() + ". Suggestion" + student.getSuggestion();
        } else {
            return "Feedback: " + student.getFeedback();
        }
    }

    @PostMapping("/upgrade")
    public String upgradeToPremium(@RequestParam String name) {
        Student student = this.studentFactory.get_student(name);
        studentService.upgradeToPremium(student);
        if (student.isPremium()) {
            return "Student " + student.getName() + " is now Premium!";
        }
        return "Student " + student.getName() + " has not yet qualified for Premium.";
    }
}
