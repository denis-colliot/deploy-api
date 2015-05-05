package fr.osb.deployapi.boot;

import fr.osb.deployapi.util.Paths;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(Paths.PACKAGE)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(final Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(final Context context) {
                final ContextEnvironment ce = new ContextEnvironment();
                ce.setName("integration/username");
                ce.setValue("admin22");
                ce.setType(String.class.getName());
                context.getNamingResources().addEnvironment(ce);
            }
        };
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