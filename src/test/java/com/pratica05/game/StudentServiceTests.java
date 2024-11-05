package com.pratica05.game;

import com.pratica05.game.dto.CreateStudentDto;
import com.pratica05.game.entities.Student;
import com.pratica05.game.entities.vo.Address;
import com.pratica05.game.repositories.StudentRepository;
import com.pratica05.game.service.CourseService;
import com.pratica05.game.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        student = new Student("Maria Julia");
        student.setId(1L);
    }

    @Test
    public void testSaveStudent() {
        CreateStudentDto dto = new CreateStudentDto();
        dto.setName("Maria Julia");
        dto.setAddress(new Address("Street", "City", "State", "11111-777"));
        dto.setActive(true);
        dto.setTotalCourses(5);
        dto.setPremium(false);
        dto.setCoins(0);
        dto.setFeedback("feedback");
        dto.setSuggestion("suggestion");
        dto.setAverageScore(7.5);
        dto.setCompletedCourses(false);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.saveStudent(dto);

        assertNotNull(savedStudent);
        assertEquals("Maria Julia", savedStudent.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testGetStudentByIdFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals("Maria Julia", foundStudent.getName());
    }

    @Test
    public void testGetStudentByIdNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(1L);
        });

        assertEquals("Student not found with id: 1", exception.getMessage());
    }

    @Test
    public void testCompleteCourseWithHighScore() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseService.isEligibleForPremium(anyInt())).thenReturn(true);

        studentService.completeCourse(1L, 8.0);

        assertEquals("You've been upgraded to Premium!", student.getFeedback());
        assertTrue(student.isPremium());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testCompleteCourseWithLowScore() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.completeCourse(1L, 6.0);

        assertEquals("Your average score is below 7.0. Keep improving to unlock more courses.", student.getFeedback());
        assertFalse(student.isPremium());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testCompleteCourseStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.completeCourse(1L, 8.0);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testUpgradeToPremiumEligible() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseService.isEligibleForPremium(anyInt())).thenReturn(true);

        studentService.upgradeToPremium(1L);

        assertTrue(student.isPremium());
        assertEquals("Congratulations! You've been upgraded to Premium.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUpgradeToPremiumNotEligible() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseService.isEligibleForPremium(anyInt())).thenReturn(false);

        studentService.upgradeToPremium(1L);

        assertFalse(student.isPremium());
        assertEquals("You need to complete 12 courses to be upgraded to Premium.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUpgradeToPremiumStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.upgradeToPremium(1L);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testParticipateInForumActiveStudent() {
        student.setActive(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.participateInForum(1L, "Nice course!");

        // Supondo que o método student.participateInForum(comment) define o feedback como "Good contribution! Keep helping others."
        assertEquals("Good contribution! Keep helping others.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testParticipateInForumInactiveStudent() {
        student.setActive(false);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.participateInForum(1L, "Nice course!");

        assertEquals("Student needs to be active to participate in the forum.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testParticipateInForumStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.participateInForum(1L, "Nice course!");
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testCalculateAchievements() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.calculateAchievements(1L, 10);

        assertEquals(10, student.getCoins());
        assertEquals("Congratulations! You earned 10 points.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testCalculateAchievementsStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.calculateAchievements(1L, 10);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testUseCoinsWithSufficientCoins() {
        student.setCoins(10);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.useCoins(1L, 5);

        assertEquals(5, student.getCoins());
        assertEquals("You have used 5 coin(s).", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUseCoinsWithInsufficientCoins() {
        student.setCoins(3);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.useCoins(1L, 5);

        assertEquals(3, student.getCoins());
        assertEquals("You don't have enough coins to use.", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUseCoinsStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.useCoins(1L, 5);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testSuggestCourses() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        String suggestion = studentService.suggestCourses(1L);

        assertEquals(student.getSuggestedCourses(), suggestion);
    }

    @Test
    public void testSuggestCoursesStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.suggestCourses(1L);
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    public void testCheckStatusAndSuggestCoursesForPremium() {
        student.setPremium(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        String result = studentService.checkStatusAndSuggestCourses(1L);

        assertEquals("You are a Premium student. " + student.getSuggestedCourses(), result);
    }

    @Test
    public void testCheckStatusAndSuggestCoursesForNonPremium() {
        student.setPremium(false);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        String result = studentService.checkStatusAndSuggestCourses(1L);

        assertEquals("Complete more courses to become Premium.", result);
    }

    @Test
    public void testCheckStatusAndSuggestCoursesStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.checkStatusAndSuggestCourses(1L);
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    public void testAddCoins() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.addCoins(1L, 10);

        assertEquals(10, student.getCoins());
        assertEquals("You've earned 10 new coin(s).", student.getFeedback());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testAddCoinsStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.addCoins(1L, 10);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, never()).save(any(Student.class));
    }
}
