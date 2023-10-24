package com.project.solution2.config;

import com.project.solution2.model.entity.Car;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration
//@ComponentScan(basePackages="com")
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Car car(){
        return new Car();
    }
//
//    @Bean
//    public User user(){
//        return new User();
//    }

}
