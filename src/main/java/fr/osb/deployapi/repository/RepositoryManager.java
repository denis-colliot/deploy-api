package fr.osb.deployapi.repository;

import java.util.List;

/**
 * Repository manager (artifactory, nexus, etc.). Provides access to repository builds and artifacts.<br>
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public interface RepositoryManager {

    /**
     * Retrieves all the builds managed by the repository manager.
     *
     * @return All the builds managed by the repository manager.
     */
    List<String> getAllBuilds();

    /**
     * Retrieves the given {@code build} builds numbers sorted by descendant number.
     *
     * @param build
     *         The build name.
     * @return The given {@code build} builds numbers.
     */
    List<Integer> getBuildNumbers(String build);

    /**
     * Retrieves the given {@code build} information.
     *
     * @param build
     *         The build name.
     * @param number
     *         The build number.
     * @return The given {@code build} information.
     */
    IsBuildInfo getBuildInfo(String build, Integer number);

    /**
     * Retrieves the given build identifier <b>deployable</b> artifact info (including download URI).<br>
     * This process may be time consuming.
     *
     * @param build
     *         The build name.
     * @param number
     *         The build number.
     * @return The given build identifier <b>deployable</b> artifact info (including download URI).
     */
    IsArtifactInfo getBuildArtifact(String build, Integer number);

}
