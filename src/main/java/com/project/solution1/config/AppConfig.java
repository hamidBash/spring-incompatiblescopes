package com.project.solution1.config;

import com.project.solution1.model.entity.Car;
import com.project.solution1.model.entity.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Car car(){
        return new Car();
    }

    @Bean
    public User user(){
        return new User();
    }

}
