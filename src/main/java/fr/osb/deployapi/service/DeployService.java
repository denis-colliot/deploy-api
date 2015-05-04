package fr.osb.deployapi.service;

/**
 * Deploy service.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public interface DeployService {

    /**
     * Deploys the given {@code build} / {@code number} corresponding artifact on given {@code env} infrastructure.
     *
     * @param env
     *         The environment key. Corresponding properties must be declared.
     * @param build
     *         The build name.
     * @param number
     *         The build number.
     */
    void deploy(String env, String build, Integer number);

}
