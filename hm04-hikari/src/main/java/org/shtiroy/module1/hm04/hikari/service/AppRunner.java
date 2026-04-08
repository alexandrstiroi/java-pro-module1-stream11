package org.shtiroy.module1.hm04.hikari.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(AppRunner.class);
    private final UserService userService;

    public AppRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.createUser("testCreate");

        userService.getAllUsers().forEach(elem -> log.info("User - {}", elem));
        userService.deleteUser(1L);
    }
}
