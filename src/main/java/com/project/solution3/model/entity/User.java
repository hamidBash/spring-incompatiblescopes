package com.project.solution3.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class User {

//    @Autowired
//    private Car car;

    @Autowired
    private ApplicationContext applicationContext;

    public Car getCar() {
        return applicationContext.getBean(Car.class);
    }

}
