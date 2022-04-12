package android.snek.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnekServer {
    public static void main(String[] args) {
        SpringApplication.run(SnekServer.class, args);
    }
}
