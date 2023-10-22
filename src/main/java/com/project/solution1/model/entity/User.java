package com.project.solution1.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class User implements ApplicationContextAware {

//    @Autowired
//    private Car car;

//    @Autowired
    private ApplicationContext applicationContext;

    public Car getCar() {
        return applicationContext.getBean(Car.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

}
