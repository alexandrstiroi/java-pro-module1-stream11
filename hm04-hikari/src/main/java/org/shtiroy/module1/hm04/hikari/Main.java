package org.shtiroy.module1.hm04.hikari;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.createUser("shtiroy");
        userService.createUser("testuser");

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        System.out.println(userService.getUser(1L));

        userService.deleteUser(1L);

        users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        context.close();
    }
}
