package HCY.CommentBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommentBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentBoardApplication.class, args);
	}

}
