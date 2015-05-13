package fr.osb.deployapi.config;

import fr.osb.deployapi.util.Paths;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Configuration
@ComponentScan(Paths.PACKAGE)
@EnableAutoConfiguration
public class AppConfig {

    // Declare beans here.

}
