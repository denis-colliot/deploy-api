package fr.osb.deployapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * Run method.
     *
     * @param args
     *         The run arguments.
     * @throws Exception
     *         If an error occurs while running the app.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}