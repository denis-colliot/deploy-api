package fr.osb.deployapi.util;

/**
 * Paths handled by the REST API.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public final class Paths {

    // --
    // PACKAGE.
    // --

    /**
     * Root <b>package</b> path.
     */
    public static final String PACKAGE = "fr.osb.deployapi";

    // --
    // HTTP PATHS.
    // --

    public static final String ROOT = "/";

    public static final String BUILDS = "/builds";

    public static final String ENVIRONMENTS = "/environments";

    public static final String REST = "/rest";

    public static final String REST_BUILD_INFO = "/build-info";

    public static final String REST_ARTIFACT_INFO = "/artifact-info";

    public static final String REST_DEPLOY = "/deploy";

    private Paths() {
        // Utility class constructor.
    }

    /**
     * Builds the global path with the given {@code paths}.
     *
     * @param paths
     *         The path(s).
     * @return The global path built with the given {@code paths}.
     */
    public static String p(final Object... paths) {

        final StringBuilder builder = new StringBuilder();
        final char delimiter = '/';

        for (final Object path : paths) {

            if (path == null) {
                continue;
            }

            final String cleanPath = path.toString().trim();

            if (builder.length() > 0 && builder.charAt(builder.length() - 1) != delimiter && cleanPath.charAt(0) != delimiter) {
                // Ensure that a path delimiter ('/') is present between paths.
                builder.append(delimiter);
            }

            builder.append(cleanPath);
        }

        return builder.toString();
    }

}
