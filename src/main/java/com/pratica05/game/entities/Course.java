package com.pratica05.game.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int points;  // Confirme que este campo está presente

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public int getPoints() {
        return points;
    }
}
