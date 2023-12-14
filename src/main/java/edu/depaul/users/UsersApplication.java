package edu.depaul.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("edu.depaul.users.model")
public class UsersApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
