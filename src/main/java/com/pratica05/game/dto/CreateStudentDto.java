package com.pratica05.game.dto;

import com.pratica05.game.entities.vo.Address;
import jakarta.validation.Valid;

public class CreateStudentDto {
    private String name;
    private boolean active;
    private int totalCourses;
    private boolean premium;
    private int coins;
    private String feedback;
    private String suggestion;
    private double averageScore;
    private boolean completedCourses;

    @Valid
    private Address address;

    // Getters e Setters para todos os campos
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(int totalCourses) {
        this.totalCourses = totalCourses;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public boolean isCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(boolean completedCourses) {
        this.completedCourses = completedCourses;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
