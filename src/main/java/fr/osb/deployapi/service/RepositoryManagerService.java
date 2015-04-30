package fr.osb.deployapi.service;

import fr.osb.deployapi.model.BuildInfo;
import fr.osb.deployapi.model.Builds;
import fr.osb.deployapi.model.BuildsNumbers;
import fr.osb.deployapi.model.FileInfo;

/**
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public interface RepositoryManagerService {

    /**
     * Retrieves all the builds managed by the repository manager.
     *
     * @return All the builds managed by the repository manager.
     */
    Builds getAllBuilds();

    /**
     * Retrieves the given {@code build} builds numbers sorted by descendant number.
     *
     * @param build
     *         The build name.
     * @return The given {@code build} builds numbers.
     */
    BuildsNumbers getBuildNumbers(String build);

    /**
     * Retrieves the given {@code build} information.
     *
     * @param build
     *         The build name.
     * @param number
     *         The build number.
     * @return The given {@code build} information.
     */
    BuildInfo getBuildInfo(String build, Integer number);

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
    FileInfo getBuildArtifact(String build, Integer number);

}
