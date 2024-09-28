package com.pratica05.game.service;

import com.pratica05.game.model.Student;
import org.springframework.stereotype.Service;
import com.pratica05.game.interfaces.service.StudentServiceInterface;

@Service
public class StudentService  implements StudentServiceInterface {

    // Metodo para registrar a participacao no fórum
    public void participateInForum(Student student, String comment) {
        if (student.isActive()) {
            student.participateInForum(comment);
        } else {
            student.setFeedback("Student needs to be active to participate in the forum.");
        }
    }

    // Metodo para calcular as conquistas do aluno
    public void calculateAchievements(Student student, int points) {
        student.receiveCoins(points); // Cada conquista pode render moedas ou pontos
        student.setFeedback("Congratulations! You earned " + points + " points.");
    }

    // Metodo para o aluno completar um curso
    public void completeCourse(Student student, double averageScore) {
        student.completeCourse(averageScore);
        if (averageScore >= 7.0) {
            student.setFeedback("Great job! You've earned 3 more courses.");
            student.checkIfPremium(); // Verifica se aluno virou Premium
            if (student.isPremium()) {
                student.setFeedback("You've been upgraded to Premium!");
            }
        } else {
            student.setFeedback("Your average score is below 7.0. Keep improving to unlock more courses.");
        }
    }

    // Metodo para verificar se o aluno pode ser promovido a Premium
    public void upgradeToPremium(Student student) {
        if (student.getTotalCourses() >= 12) {
            student.setPremium(true);
            student.setFeedback("Congratulations! You've been upgraded to Premium.");
        } else {
            student.setFeedback("You need to complete 12 courses to be upgraded to Premium.");
        }
    }

    // Metodo para o aluno usar moedas para novos cursos
    public void useCoins(Student student, int numberOfCoins) {
        if (student.getCoins() >= numberOfCoins) {
            student.useCoins(numberOfCoins);
            student.setFeedback("You have used " + numberOfCoins + " coin(s).");
        } else {
            student.setFeedback("You don't have enough coins to use.");
        }
    }

    // Metodo para sugerir novos cursos com base nas moedas restantes
    public String suggestCourses(Student student) {
        return student.getSuggestedCourses();
    }

    // Metodo para verificar status e sugerir novos cursos
    public String checkStatusAndSuggestCourses(Student student) {
        if (student.isPremium()) {
            return "You are a Premium student. " + suggestCourses(student);
        } else {
            return "Complete more courses to become Premium.";
        }
    }

    // Metodo para adicionar moedas ao saldo do aluno
    public void addCoins(Student student, int coins) {
        student.receiveCoins(coins);
        student.setFeedback("You've earned " + coins + " new coin(s).");
    }
}
