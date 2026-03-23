package mk.ukim.finki.wp.emtlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmtLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmtLabApplication.class, args);
    }
}
