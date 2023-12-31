package com.project.solution1;

import com.project.solution1.config.AppConfig;
import com.project.solution1.model.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        User user1 = context.getBean(User.class);
        System.out.println(user1.getCar());

        Thread.sleep(2000);
        System.out.println(user1.getCar());

        Thread.sleep(1000);
        User user2 = context.getBean(User.class);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user2.getCar());
        //car not created in each time getting user from context
    }
}
