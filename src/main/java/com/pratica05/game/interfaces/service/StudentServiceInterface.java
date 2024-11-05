package com.pratica05.game.interfaces.service;

public interface StudentServiceInterface {
    void participateInForum(Long studentId, String comment);
    void calculateAchievements(Long studentId, int points);
    void completeCourse(Long studentId, double averageScore);
    void upgradeToPremium(Long studentId);
    void useCoins(Long studentId, int numberOfCoins);
    String suggestCourses(Long studentId);
    String checkStatusAndSuggestCourses(Long studentId);
    void addCoins(Long studentId, int coins);
}
