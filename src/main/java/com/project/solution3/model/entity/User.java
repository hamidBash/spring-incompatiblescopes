package com.project.solution3.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

public class User {

    @Autowired
    @Qualifier("car3")
    private Car car;


    public Car getCar() {
        return car;
    }

}
