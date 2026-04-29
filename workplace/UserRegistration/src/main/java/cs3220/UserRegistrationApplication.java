package cs3220;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point.
 * @SpringBootApplication enables auto-configuration, component scanning,
 * and configuration. Embedded Tomcat starts automatically.
 */
@SpringBootApplication
public class UserRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationApplication.class, args);
    }
}
