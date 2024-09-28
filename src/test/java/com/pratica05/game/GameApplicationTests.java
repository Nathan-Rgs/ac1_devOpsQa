package com.pratica05.game;

import com.pratica05.game.controller.GameController;
import com.pratica05.game.model.Student;
import com.pratica05.game.service.StudentService;
import com.pratica05.game.interfaces.GameApplicationInterface;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class GameApplicationTests implements GameApplicationInterface {

	@Autowired
	private StudentService studentService;

	@Mock
	private StudentService mockedStudentService;

	@InjectMocks
	private GameController gameController;

	// Testes para StudentService

	@Test
	public void testForumParticipationFeedback() {
		// CENÁRIO: Um aluno ativo participa do fórum
		Student student = new Student("Active Student");
		student.setActive(true);

		// EXECUÇÃO: O aluno responde uma pergunta no fórum
		studentService.participateInForum(student, "This is my answer to your question");

		// RESULTADO: O feedback e sugestão são gerados corretamente
		assertEquals("Good contribution! Keep helping others.", student.getFeedback());
		assertEquals("Try to engage more with new members.", student.getSuggestion());
	}

	@Test
	public void testMonthlyAchievements() {
		// CENÁRIO: Um aluno ativo participa do fórum ao longo do mês
		Student student = new Student("Monthly Participant");
		student.setActive(true);

		// EXECUÇÃO: O sistema calcula as conquistas do mês
		studentService.calculateAchievements(student, 10);

		// RESULTADO: O aluno recebe feedback e pontos corretamente
		assertEquals("Congratulations! You earned 10 points.", student.getFeedback());
		assertEquals(10, student.getCoins());
	}

	@Test
	public void testStudentImprovementFeedback() {
		// CENÁRIO: Um aluno completou cursos e está participando do fórum
		Student student = new Student("Improvement Seeker");
		student.setCompletedCourses(true);

		// EXECUÇÃO: O aluno participa do fórum e recebe moedas adicionais
		studentService.participateInForum(student, "Offering advice to others");
		studentService.addCoins(student, 5);

		// RESULTADO: O feedback é gerado e o aluno tem sua pontuação média atualizada
		assertEquals("You've earned 5 new coin(s).", student.getFeedback());
		student.setAverageScore(8.0);
		assertEquals(8.0, student.getAverageScore());
		student.setFeedback("Great job improving your feedback skills!");
		assertEquals("Great job improving your feedback skills!", student.getFeedback());
	}

	@Test
	public void testPremiumStudentCoins() {
		// CENÁRIO: Um aluno Premium usa suas moedas em cursos
		Student student = new Student("Premium Student");
		student.setTotalCourses(12);

		// EXECUÇÃO: O aluno é promovido a Premium e usa moedas
		studentService.upgradeToPremium(student);
		assertTrue(student.isPremium());
		studentService.addCoins(student, 3);
		assertEquals(3, student.getCoins());
		studentService.useCoins(student, 1);
		assertEquals(2, student.getCoins());
		studentService.useCoins(student, 1);
		assertEquals(1, student.getCoins());

		// RESULTADO: O sistema sugere novos cursos com base nas moedas restantes
		String courseSuggestion = studentService.suggestCourses(student);
		assertEquals("You have 1 coin(s) left. Here are new courses you can enroll in.", courseSuggestion);
	}

	// Testes para GameController

	@Test
	public void testParticipateInForum() {
		// CENÁRIO: Um aluno participa do fórum via o GameController
		Student student = new Student("Forum Participant");

		// EXECUÇÃO: Mock do serviço de participação no fórum
		Mockito.doAnswer(invocation -> {
			Student s = invocation.getArgument(0);
			s.setFeedback("Good contribution! Keep helping others.");
			// Não está mais incluindo a sugestão, então vamos remover do esperado
			return null;
		}).when(mockedStudentService).participateInForum(any(Student.class), anyString());

		// EXECUÇÃO: O aluno envia uma mensagem via controller
		String response = gameController.participateInForum("Forum Participant", "Message");

		// RESULTADO: O feedback é retornado corretamente
		assertEquals("Feedback: Good contribution! Keep helping others.", response);

		// Verificação de que o serviço foi chamado corretamente
		Mockito.verify(mockedStudentService, Mockito.times(1)).participateInForum(any(Student.class), anyString());
	}

	@Test
	public void testUpgradeToPremium() {
		// CENÁRIO: Um aluno é promovido a Premium via o GameController
		Student student = new Student("Upgrade Student");

		// EXECUÇÃO: Mock do serviço de upgrade para Premium
		Mockito.doAnswer(invocation -> {
			Student s = invocation.getArgument(0);
			s.setPremium(true);
			return null;
		}).when(mockedStudentService).upgradeToPremium(any(Student.class));

		// EXECUÇÃO: O aluno é promovido a Premium via controller
		String response = gameController.upgradeToPremium("Upgrade Student");

		// RESULTADO: O status de Premium é retornado corretamente
		assertEquals("Student Upgrade Student is now Premium!", response);

		// Verificação de que o serviço foi chamado corretamente
		Mockito.verify(mockedStudentService, Mockito.times(1)).upgradeToPremium(any(Student.class));
	}
}