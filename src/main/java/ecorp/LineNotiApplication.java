package ecorp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class LineNotiApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(LineNotiApplication.class, args);
    }

}

