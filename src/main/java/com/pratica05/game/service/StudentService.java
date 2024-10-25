package com.pratica05.game.service;

import com.pratica05.game.dto.CreateStudentDto;
import com.pratica05.game.entities.Student;
import com.pratica05.game.entities.vo.Address;
import com.pratica05.game.repositories.StudentRepository;
import com.pratica05.game.interfaces.service.StudentServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    private final CourseService courseService;

    public Student saveStudent(CreateStudentDto studentDto) {
        // Converte o DTO para a entidade Student
        Student student = new Student(studentDto.getName());
        student.setAddress(studentDto.getAddress());
        student.setActive(studentDto.isActive());
        student.setTotalCourses(studentDto.getTotalCourses());
        student.setPremium(studentDto.isPremium());
        student.setCoins(studentDto.getCoins());
        student.setFeedback(studentDto.getFeedback());
        student.setSuggestion(studentDto.getSuggestion());
        student.setAverageScore(studentDto.getAverageScore());
        student.setCompletedCourses(studentDto.isCompletedCourses());

        return studentRepository.save(student);
    }


    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    }

    @Override
    public void completeCourse(Long studentId, double averageScore) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.completeCourse(averageScore);
            if (averageScore >= 7.0) {
                student.setFeedback("Great job! You've earned 3 more courses.");

                // Checa a elegibilidade para Premium usando o CourseService
                if (courseService.isEligibleForPremium(student.getTotalCourses())) {
                    student.setPremium(true);
                    student.setFeedback("You've been upgraded to Premium!");
                }
            } else {
                student.setFeedback("Your average score is below 7.0. Keep improving to unlock more courses.");
            }
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    @Override
    public void upgradeToPremium(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Verifica se o aluno é elegível para Premium usando CourseService
            if (courseService.isEligibleForPremium(student.getTotalCourses())) {
                student.setPremium(true);
                student.setFeedback("Congratulations! You've been upgraded to Premium.");
            } else {
                student.setFeedback("You need to complete 12 courses to be upgraded to Premium.");
            }
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para registrar a participação no fórum
    @Override
    public void participateInForum(Long studentId, String comment) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (student.isActive()) {
                student.participateInForum(comment);
            } else {
                student.setFeedback("Student needs to be active to participate in the forum.");
            }
            studentRepository.save(student);  // Atualiza o aluno no banco
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para calcular as conquistas do aluno
    @Override
    public void calculateAchievements(Long studentId, int points) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.receiveCoins(points); // Cada conquista pode render moedas ou pontos
            student.setFeedback("Congratulations! You earned " + points + " points.");
            studentRepository.save(student);  // Atualiza o aluno no banco
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para o aluno usar moedas para novos cursos
    @Override
    public void useCoins(Long studentId, int numberOfCoins) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (student.getCoins() >= numberOfCoins) {
                student.useCoins(numberOfCoins);
                student.setFeedback("You have used " + numberOfCoins + " coin(s).");
            } else {
                student.setFeedback("You don't have enough coins to use.");
            }
            studentRepository.save(student);  // Atualiza o aluno no banco
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para sugerir novos cursos com base nas moedas restantes
    @Override
    public String suggestCourses(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getSuggestedCourses();
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para verificar status e sugerir novos cursos
    @Override
    public String checkStatusAndSuggestCourses(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (student.isPremium()) {
                return "You are a Premium student. " + suggestCourses(studentId);
            } else {
                return "Complete more courses to become Premium.";
            }
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    // Método para adicionar moedas ao saldo do aluno
    @Override
    public void addCoins(Long studentId, int coins) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.receiveCoins(coins);
            student.setFeedback("You've earned " + coins + " new coin(s).");
            studentRepository.save(student);  // Atualiza o aluno no banco
        } else {
            throw new RuntimeException("Student not found");
        }
    }
}
