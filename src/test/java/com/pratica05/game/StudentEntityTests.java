package com.pratica05.game;

import com.pratica05.game.entities.Student;
import com.pratica05.game.entities.vo.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEntityTests {

    @Test
    public void testStudentCreation() {
        Student student = new Student("Maria Julia");

        assertNotNull(student);
        assertEquals("Maria Julia", student.getName());
        assertTrue(student.isActive());
        assertEquals(0, student.getTotalCourses());
        assertFalse(student.isPremium());
        assertEquals(0, student.getCoins());
    }

    @Test
    public void testAddCoins() {
        Student student = new Student("Maria Julia");
        student.receiveCoins(5);

        assertEquals(5, student.getCoins());
    }

    @Test
    public void testUseCoinsWithSufficientCoins() {
        Student student = new Student("Maria Julia");
        student.receiveCoins(10);
        student.useCoins(5);

        assertEquals(5, student.getCoins());
    }

    @Test
    public void testUseCoinsWithInsufficientCoins() {
        Student student = new Student("Maria Julia");
        student.receiveCoins(3);
        student.useCoins(5);

        assertEquals(3, student.getCoins());
        assertEquals("Not enough coins.", student.getFeedback());
    }

    @Test
    public void testCompleteCourseWithHighScore() {
        Student student = new Student("Maria Julia");
        student.completeCourse(8.0);

        assertEquals(3, student.getTotalCourses());
        assertEquals(8.0, student.getAverageScore());
        assertEquals("Great job! You've earned 3 more courses.", student.getFeedback());
    }

    @Test
    public void testCompleteCourseWithHighScoreAndPremiumStatus() {
        Student student = new Student("Maria Julia");
        student.setTotalCourses(10);
        student.completeCourse(8.0);

        assertEquals(13, student.getTotalCourses());
        assertEquals(8.0, student.getAverageScore());
        assertTrue(student.isPremium());
        assertEquals("Great job! You've earned 3 more courses. You've been upgraded to Premium!", student.getFeedback());
    }

    @Test
    public void testCompleteCourseWithLowScore() {
        Student student = new Student("Maria Julia");
        student.completeCourse(6.5);

        assertEquals(0, student.getTotalCourses());
        assertEquals(6.5, student.getAverageScore());
        assertEquals("Your average score is below 7.0. Keep improving to unlock more courses.", student.getFeedback());
    }

    @Test
    public void testCheckIfPremium() {
        Student student = new Student("Maria Julia");
        student.setTotalCourses(12);
        student.checkIfPremium();

        assertTrue(student.isPremium());
    }

    @Test
    public void testCheckIfNotPremium() {
        Student student = new Student("Maria Julia");
        student.setTotalCourses(5);
        student.checkIfPremium();

        assertFalse(student.isPremium());
    }

    @Test
    public void testParticipateInForumAsActiveStudent() {
        Student student = new Student("Maria Julia");
        student.setActive(true);
        student.participateInForum("Nice course!");

        assertEquals("Good contribution! Keep helping others.", student.getFeedback());
        assertEquals("Try to engage more with new members.", student.getSuggestion());
    }

    @Test
    public void testParticipateInForumAsInactiveStudent() {
        Student student = new Student("Maria Julia");
        student.setActive(false);
        student.participateInForum("Nice course!");

        assertEquals("You need to be active to participate in the forum.", student.getFeedback());
    }

    @Test
    public void testGetSuggestedCoursesWithCoins() {
        Student student = new Student("Maria Julia");
        student.setCoins(5);

        assertEquals("You have 5 coin(s) left. Here are new courses you can enroll in.", student.getSuggestedCourses());
    }

    @Test
    public void testGetSuggestedCoursesWithoutCoins() {
        Student student = new Student("Maria Julia");
        student.setCoins(0);

        assertEquals("You have no coins left. Please earn more coins to unlock new courses.", student.getSuggestedCourses());
    }

    @Test
    public void testSetAndRetrieveAddress() {
        Student student = new Student("Maria Julia");
        Address address = new Address("123 Street", "City", "State", "11111-777");
        student.setAddress(address);

        assertNotNull(student.getAddress());
        assertEquals("123 Street", student.getAddress().getStreet());
        assertEquals("City", student.getAddress().getCity());
        assertEquals("State", student.getAddress().getState());
        assertEquals("11111-777", student.getAddress().getPostalCode());
    }

    @Test
    public void testIsCompletedCourses() {
        Student student = new Student("Maria Julia");
        student.setCompletedCourses(true);
        assertTrue(student.isCompletedCourses());

        student.setCompletedCourses(false);
        assertFalse(student.isCompletedCourses());
    }

    @Test
    public void testHasCompletedCourses() {
        Student student = new Student("Maria Julia");
        student.setCompletedCourses(true);
        assertTrue(student.hasCompletedCourses());

        student.setCompletedCourses(false);
        assertFalse(student.hasCompletedCourses());
    }
}