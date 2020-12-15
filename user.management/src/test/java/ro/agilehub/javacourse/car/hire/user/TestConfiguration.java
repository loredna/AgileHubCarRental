package ro.agilehub.javacourse.car.hire.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "ro/agilehub/javacourse/car/hire/user")
@EnableMongoRepositories("ro/agilehub/javacourse/car/hire/user/repository")
@SpringBootApplication
public class TestConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(TestConfiguration.class, args);
    }
}
