package com.pratica05.game.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pratica05.game.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

