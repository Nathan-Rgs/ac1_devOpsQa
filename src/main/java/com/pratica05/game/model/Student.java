package com.pratica05.game.model;

public class Student {
    private String name;
    private boolean isActive;
    private int totalCourses;
    private boolean isPremium;
    private int coins;
    private String feedback;
    private String suggestion;
    private double averageScore;  // Adicionando o atributo para a pontuação média
    private boolean completedCourses; // Adicionando o atributo para controle de cursos completos

    // Construtor
    public Student(String name) {
        this.name = name;
        this.isActive = false;
        this.totalCourses = 0;
        this.isPremium = false;
        this.coins = 0;
        this.feedback = "";
        this.suggestion = "";
        this.averageScore = 0.0;  // Inicializando a pontuação média
        this.completedCourses = false; // Inicializando os cursos completados
    }

    // Getters e Setters

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(int totalCourses) {
        this.totalCourses = totalCourses;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
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

    // Adicionando métodos que faltam
    // Metodo para definir se o aluno completou cursos
    public void setCompletedCourses(boolean completedCourses) {
        this.completedCourses = completedCourses;
    }

    public boolean hasCompletedCourses() {
        return this.completedCourses;
    }

    // Metodo para definir a pontuação média do aluno
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    // Metodo para recuperar a pontuação média do aluno
    public double getAverageScore() {
        return this.averageScore;
    }

    // Metodos de ação

    // Participação no fórum
    public void participateInForum(String comment) {
        if (this.isActive) {
            this.feedback = "Good contribution! Keep helping others.";
            this.suggestion = "Try to engage more with new members.";
        } else {
            this.feedback = "You need to be active to participate in the forum.";
        }
    }

    // Receber moedas
    public void receiveCoins(int numberOfCoins) {
        this.coins += numberOfCoins;
    }

    // Gastar moedas
    public void useCoins(int numberOfCoins) {
        if (this.coins >= numberOfCoins) {
            this.coins -= numberOfCoins;
        } else {
            this.feedback = "Not enough coins.";
        }
    }

    // Sugestão de novos cursos baseada nas moedas restantes
    public String getSuggestedCourses() {
        if (this.coins > 0) {
            return "You have " + this.coins + " coin(s) left. Here are new courses you can enroll in.";
        } else {
            return "You have no coins left. Please earn more coins to unlock new courses.";
        }
    }

    // Atualizar status de Premium
    public void checkIfPremium() {
        if (this.totalCourses >= 12) {
            this.isPremium = true;
        }
    }

    // Completar curso e verificar se o aluno se torna Premium
    public void completeCourse(double averageScore) {
        this.averageScore = averageScore; // Atualiza a pontuação média do aluno
        if (averageScore >= 7.0) {
            this.totalCourses += 3; // O aluno ganha mais 3 cursos se a média for maior que 7
            checkIfPremium(); // Verifica se o aluno se tornou premium
            this.feedback = "Great job! You've earned 3 more courses.";
            if (this.isPremium) {
                this.feedback += " You've been upgraded to Premium!";
            }
        } else {
            this.feedback = "Your average score is below 7.0. Keep improving to unlock more courses.";
        }
    }
}