package com.project.solution2.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
