package com.pratica05.game.interfaces;

// Validar implementação dos testes no roteiro
// Utilizar BDD criado para cada feature
public interface CourseControllerInterface {
    void createCourse_ShouldReturnSavedCourse();
    void getCourseById_ShouldReturnCourse_WhenCourseExists();
    void getCourseById_ShouldThrowException_WhenCourseNotFound();
    void addCourse_ShouldReturnSavedCourse();
    void getCoursesByStudentId_ShouldReturnListOfCourses();
    void isEligibleForPremium_ShouldReturnEligibilityStatus();
    void isEligibleForPremium_ShouldReturnFalse_WhenNotEligible();
}
