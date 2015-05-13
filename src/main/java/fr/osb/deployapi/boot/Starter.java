package fr.osb.deployapi.boot;

import fr.osb.deployapi.config.AppConfig;
import fr.osb.deployapi.service.DeployService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * <p>Starter class executing the program.</p>
 * <p>
 * Two ways to execute the program in command line (JVM required):
 * <pre>
 * # Deploys the given version of the build on the given environment:
 * java -jar deploy-api-0.0.1-SNAPSHOT.jar {env} {build_name} {build_version}
 *
 * # Deploys the latest version of the build on the given environment:
 * java -jar deploy-api-0.0.1-SNAPSHOT.jar {env} {build_name}
 * </pre>
 * </p>
 */
@Import(AppConfig.class)
public final class Starter implements CommandLineRunner {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    /**
     * Run method.
     *
     * @param args
     *         The run arguments.
     * @throws Exception
     *         If an error occurs while running the app.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    /**
     * Injected service.
     */
    @Autowired
    private DeployService deployService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(final String... args) throws Exception {

        LOGGER.info("Program arguments: {}", ArrayUtils.toString(args));

        if (ArrayUtils.isEmpty(args)) {
            throw new IllegalArgumentException("No arguments.");
        }

        // TODO call deploy service with program arguments.
    }
}