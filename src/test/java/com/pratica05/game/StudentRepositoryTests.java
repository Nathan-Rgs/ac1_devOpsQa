package com.pratica05.game;

import com.pratica05.game.entities.Student;
import com.pratica05.game.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveStudent() {
        Student student = new Student("Maria Julia");
        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals("Maria Julia", savedStudent.getName());
    }

    @Test
    public void testFindStudentById() {
        Student student = new Student("Maria Julia");
        Student savedStudent = studentRepository.save(student);

        Student foundStudent = studentRepository.findById(savedStudent.getId()).orElse(null);

        assertNotNull(foundStudent);
        assertEquals(savedStudent.getId(), foundStudent.getId());
        assertEquals("Maria Julia", foundStudent.getName());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student("Maria Julia");
        Student savedStudent = studentRepository.save(student);

        savedStudent.setName("Maria Julia Updated");
        Student updatedStudent = studentRepository.save(savedStudent);

        assertNotNull(updatedStudent);
        assertEquals(savedStudent.getId(), updatedStudent.getId());
        assertEquals("Maria Julia Updated", updatedStudent.getName());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student("Maria Julia");
        Student savedStudent = studentRepository.save(student);

        studentRepository.delete(savedStudent);

        assertFalse(studentRepository.findById(savedStudent.getId()).isPresent());
    }
}
