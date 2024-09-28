package com.pratica05.game;

import com.pratica05.game.model.Student;
import com.pratica05.game.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameApplicationTests {

	@Autowired
	private StudentService studentService;

	@Test
	public void testForumParticipationFeedback() {
		Student student = new Student("Active Student");
		student.setActive(true);
		studentService.participateInForum(student, "This is my answer to your question");
		assertEquals("Good contribution! Keep helping others.", student.getFeedback());
		assertEquals("Try to engage more with new members.", student.getSuggestion());
	}

	@Test
	public void testMonthlyAchievements() {
		Student student = new Student("Monthly Participant");
		student.setActive(true);
		studentService.calculateAchievements(student, 10);
		assertEquals("Congratulations! You earned 10 points.", student.getFeedback());
		assertEquals(10, student.getCoins());
	}

	@Test
	public void testStudentImprovementFeedback() {
		Student student = new Student("Improvement Seeker");
		student.setCompletedCourses(true);
		studentService.participateInForum(student, "Offering advice to others");
		studentService.addCoins(student, 5);
		assertEquals("You've earned 5 new coin(s).", student.getFeedback());
		student.setAverageScore(8.0);
		assertEquals(8.0, student.getAverageScore());
		student.setFeedback("Great job improving your feedback skills!");
		assertEquals("Great job improving your feedback skills!", student.getFeedback());
	}

	@Test
	public void testPremiumStudentCoins() {
		Student student = new Student("Premium Student");
		student.setTotalCourses(12);
		studentService.upgradeToPremium(student);
		assertTrue(student.isPremium());
		studentService.addCoins(student, 3);
		assertEquals(3, student.getCoins());
		studentService.useCoins(student, 1);
		assertEquals(2, student.getCoins());
		studentService.useCoins(student, 1);
		assertEquals(1, student.getCoins());
		String courseSuggestion = studentService.suggestCourses(student);
		assertEquals("You have 1 coin(s) left. Here are new courses you can enroll in.", courseSuggestion);
	}
}
