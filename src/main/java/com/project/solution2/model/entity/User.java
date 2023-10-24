package com.project.solution2.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class User {

//    @Autowired
//    private Car car;


    public Car getCar() {
        Car car = getMyNewCar();
        return car;
    }

    @Lookup
    public Car getMyNewCar(){
        return null;
    }
}
