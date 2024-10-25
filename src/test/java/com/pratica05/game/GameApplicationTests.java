package com.pratica05.game;

import com.pratica05.game.controller.StudentController;
import com.pratica05.game.dto.CreateStudentDto;
import com.pratica05.game.entities.Student;
import com.pratica05.game.service.StudentService;
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
class GameApplicationTests {

	@Autowired
	private StudentService studentService;

	@Mock
	private StudentService mockedStudentService;

	@InjectMocks
	private StudentController studentController;

	@Test
	public void testForumParticipationFeedback() {
		// CENÁRIO: Criando um DTO de estudante ativo
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Active Student");
		studentDto.setActive(true);
		studentDto.setCoins(0);

		// Converter DTO para Student e salvar
		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			String comment = invocation.getArgument(1);

			Student s = mockedStudentService.getStudentById(id);
			s.setFeedback("Good contribution! Keep helping others.");
			s.setSuggestion("Try to engage more with new members.");
			return null;
		}).when(mockedStudentService).participateInForum(studentId, "This is my answer to your question");

		String response = studentController.participateInForum(studentId, "This is my answer to your question").getBody();
		assertEquals("Participation registered successfully.", response);
		Mockito.verify(mockedStudentService).participateInForum(studentId, "This is my answer to your question");
	}

	@Test
	public void testMonthlyAchievements() {
		// Criando um DTO para estudante ativo
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Monthly Participant");
		studentDto.setActive(true);

		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			int points = invocation.getArgument(1);

			Student s = mockedStudentService.getStudentById(id);
			s.setFeedback("Congratulations! You earned 10 points.");
			s.setCoins(points);
			return null;
		}).when(mockedStudentService).calculateAchievements(studentId, 10);

		studentController.calculateAchievements(studentId, 10);

		Student updatedStudent = mockedStudentService.getStudentById(studentId);
		assertEquals("Congratulations! You earned 10 points.", updatedStudent.getFeedback());
		assertEquals(10, updatedStudent.getCoins());
		Mockito.verify(mockedStudentService).calculateAchievements(studentId, 10);
	}

	@Test
	public void testStudentImprovementFeedback() {
		// Criando um DTO para estudante que completou cursos
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Improvement Seeker");
		studentDto.setCompletedCourses(true);

		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			String comment = invocation.getArgument(1);

			Student s = mockedStudentService.getStudentById(id);
			s.setFeedback("You've earned 5 new coin(s).");
			return null;
		}).when(mockedStudentService).participateInForum(studentId, "Offering advice to others");

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			int coins = invocation.getArgument(1);

			Student s = mockedStudentService.getStudentById(id);
			s.setCoins(s.getCoins() + coins);
			return null;
		}).when(mockedStudentService).addCoins(studentId, 5);

		studentController.participateInForum(studentId, "Offering advice to others");
		studentController.addCoins(studentId, 5);

		Student updatedStudent = mockedStudentService.getStudentById(studentId);
		assertEquals("You've earned 5 new coin(s).", updatedStudent.getFeedback());
		assertEquals(5, updatedStudent.getCoins());

		Mockito.verify(mockedStudentService).participateInForum(studentId, "Offering advice to others");
		Mockito.verify(mockedStudentService).addCoins(studentId, 5);
	}

	@Test
	public void testPremiumStudentCoins() {
		// Criando um DTO para estudante com cursos totais para Premium
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Premium Student");
		studentDto.setTotalCourses(12);

		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			Student s = mockedStudentService.getStudentById(id);
			s.setPremium(true);
			return null;
		}).when(mockedStudentService).upgradeToPremium(studentId);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			int coins = invocation.getArgument(1);
			Student s = mockedStudentService.getStudentById(id);
			s.setCoins(s.getCoins() + coins);
			return null;
		}).when(mockedStudentService).addCoins(studentId, 3);

		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			int coins = invocation.getArgument(1);
			Student s = mockedStudentService.getStudentById(id);
			s.setCoins(s.getCoins() - coins);
			return null;
		}).when(mockedStudentService).useCoins(studentId, 1);

		studentController.upgradeToPremium(studentId);
		studentController.addCoins(studentId, 3);
		studentController.useCoins(studentId, 1);
		studentController.useCoins(studentId, 1);

		Student updatedStudent = mockedStudentService.getStudentById(studentId);
		assertTrue(updatedStudent.isPremium());
		assertEquals(1, updatedStudent.getCoins());

		Mockito.verify(mockedStudentService).upgradeToPremium(studentId);
		Mockito.verify(mockedStudentService).addCoins(studentId, 3);
		Mockito.verify(mockedStudentService, Mockito.times(2)).useCoins(studentId, 1);
	}

	// Controller Tests
	@Test
	public void testParticipateInForum() {
		// Configuração: Criação do DTO do estudante
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Forum Participant");
		studentDto.setActive(true);

		// Simula salvar o estudante e obter o ID
		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		// Configuração do mock para retornar o estudante pelo ID
		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		// Simulação da participação no fórum
		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			String comment = invocation.getArgument(1);
			Student s = mockedStudentService.getStudentById(id);
			s.setFeedback("Good contribution! Keep helping others.");
			return null;
		}).when(mockedStudentService).participateInForum(any(Long.class), anyString());

		// Execução: Chamando o método do controlador
		String response = studentController.participateInForum(studentId, "Message").getBody();

		// Verificação: Confirmação de resposta e interação
		assertEquals("Participation registered successfully.", response);
		Mockito.verify(mockedStudentService).participateInForum(studentId, "Message");
	}

	// Test for upgrading to Premium
	@Test
	public void testUpgradeToPremium() {
		// Configuração: Criação do DTO do estudante
		CreateStudentDto studentDto = new CreateStudentDto();
		studentDto.setName("Upgrade Student");
		studentDto.setTotalCourses(12);

		// Salva o estudante e obtém o ID
		Student student = studentService.saveStudent(studentDto);
		Long studentId = student.getId();

		// Configuração do mock para retorno do estudante salvo pelo ID
		Mockito.when(mockedStudentService.getStudentById(studentId)).thenReturn(student);

		// Simulação da promoção para Premium
		Mockito.doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			Student s = mockedStudentService.getStudentById(id);
			s.setPremium(true);
			return null;
		}).when(mockedStudentService).upgradeToPremium(any(Long.class));

		// Execução: Chamando o método do controlador
		String response = studentController.upgradeToPremium(studentId).getBody();

		// Verificação: Confirmação de resposta e interação
		assertEquals("Student upgraded to Premium successfully.", response);
		Mockito.verify(mockedStudentService).upgradeToPremium(studentId);
	}
}
