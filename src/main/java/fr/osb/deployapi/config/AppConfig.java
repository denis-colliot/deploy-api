package fr.osb.deployapi.config;

import fr.osb.deployapi.util.Paths;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application configuration.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@SpringBootApplication
@ComponentScan(Paths.PACKAGE)
public class AppConfig {

    // Declare beans here.

}
