package com.pratica05.game.entities;

import com.pratica05.game.entities.vo.Address;
import com.pratica05.game.interfaces.model.StudentInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student implements StudentInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean isActive = false;
    private int totalCourses = 0;
    private boolean isPremium = false;
    private int coins = 0;
    private String feedback = "";
    private String suggestion = "";
    private double averageScore = 0.0;
    private boolean completedCourses = false;

    @OneToMany(mappedBy = "student")
    private List<Course> courses;

    @Embedded
    private Address address;  // Embedding a Value Object

    public Student(String name) {
        this.name = name;
        this.isActive = true;
        this.totalCourses = 0;
        this.isPremium = false;
        this.coins = 0;
        this.feedback = "";
        this.suggestion = "";
        this.averageScore = 0.0;
        this.completedCourses = false;
    }
    // Esse método já é gerado automaticamente pelo Lombok, devido à anotação @Data
    public Long getId() {
        return id;
    }


    // Implementação de métodos da interface StudentInterface
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int getTotalCourses() {
        return totalCourses;
    }

    @Override
    public void setTotalCourses(int totalCourses) {
        this.totalCourses = totalCourses;
    }

    @Override
    public boolean isPremium() {
        return isPremium;
    }

    @Override
    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String getFeedback() {
        return feedback;
    }

    @Override
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public double getAverageScore() {
        return averageScore;
    }

    @Override
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public boolean hasCompletedCourses() {
        return completedCourses;
    }

    @Override
    public void setCompletedCourses(boolean completedCourses) {
        this.completedCourses = completedCourses;
    }

    @Override
    public String getSuggestedCourses() {
        return coins > 0
                ? "You have " + coins + " coin(s) left. Here are new courses you can enroll in."
                : "You have no coins left. Please earn more coins to unlock new courses.";
    }

    @Override
    public void participateInForum(String comment) {
        if (isActive) {
            feedback = "Good contribution! Keep helping others.";
            suggestion = "Try to engage more with new members.";
        } else {
            feedback = "You need to be active to participate in the forum.";
        }
    }

    @Override
    public void receiveCoins(int numberOfCoins) {
        coins += numberOfCoins;
    }

    @Override
    public void useCoins(int numberOfCoins) {
        if (coins >= numberOfCoins) {
            coins -= numberOfCoins;
        } else {
            feedback = "Not enough coins.";
        }
    }

    @Override
    public void checkIfPremium() {
        if (totalCourses >= 12) {
            isPremium = true;
        }
    }

    @Override
    public void completeCourse(double averageScore) {
        this.averageScore = averageScore;
        if (averageScore >= 7.0) {
            totalCourses += 3;
            checkIfPremium();
            feedback = "Great job! You've earned 3 more courses.";
            if (isPremium) {
                feedback += " You've been upgraded to Premium!";
            }
        } else {
            feedback = "Your average score is below 7.0. Keep improving to unlock more courses.";
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isCompletedCourses() {
        return completedCourses;
    }
}
