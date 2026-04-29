package house.wammys.youpronoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class YouPronoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouPronoApiApplication.class, args);
	}

}
