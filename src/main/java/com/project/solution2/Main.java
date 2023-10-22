package com.project.solution2;

import com.project.solution2.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        User user1 = context.getBean(User.class);
        System.out.println(user1.getCar());

        Thread.sleep(2000);
        System.out.println(user1.getCar());

        User user2 = context.getBean(User.class);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user2.getCar());
        //car not created in each time getting user from context
    }
}
