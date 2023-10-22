package com.project.solution3.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class User {

    @Autowired
    private Car car;


    public Car getCar() {
        return car;
    }

}
