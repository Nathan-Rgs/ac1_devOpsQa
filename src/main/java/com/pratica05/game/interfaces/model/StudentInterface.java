package com.pratica05.game.interfaces.model;

public interface StudentInterface {
    public String getName();
    public boolean isActive();
    public void setActive(boolean isActive);
    public int getTotalCourses();
    public void setTotalCourses(int totalCourses);
    public boolean isPremium();
    public void setPremium(boolean isPremium);
    public int getCoins();
    public void setCoins(int coins);
    public String getFeedback();
    public void setFeedback(String feedback);
    public String getSuggestion();
    public double getAverageScore();
    public String getSuggestedCourses();

    public void setSuggestion(String suggestion);
    public void setCompletedCourses(boolean completedCourses);
    public void setAverageScore(double averageScore);

    public boolean hasCompletedCourses();
    public void checkIfPremium();

    public void participateInForum(String comment);
    public void receiveCoins(int numberOfCoins);
    public void useCoins(int numberOfCoins);
    public void completeCourse(double averageScore);

}
