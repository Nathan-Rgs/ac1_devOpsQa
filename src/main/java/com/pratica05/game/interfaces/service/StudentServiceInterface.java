package com.pratica05.game.interfaces.service;
import com.pratica05.game.model.Student;

public interface StudentServiceInterface {
    public void participateInForum(Student student, String comment);
    public void calculateAchievements(Student student, int points);
    public void completeCourse(Student student, double averageScore);
    public void upgradeToPremium(Student student);
    public void useCoins(Student student, int numberOfCoins);
    public String suggestCourses(Student student);
    public String checkStatusAndSuggestCourses(Student student);
    public void addCoins(Student student, int coins);
}
