package com.pratica05.game.factory;

import com.pratica05.game.entities.Student;

public class StudentFactory {

    public Student get_student(String name){
        return new Student(name);
    };
};
