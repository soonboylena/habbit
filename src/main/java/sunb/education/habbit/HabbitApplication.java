package sunb.education.habbit;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

import java.io.Console;

@SpringBootApplication
public class HabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabbitApplication.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("***", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
