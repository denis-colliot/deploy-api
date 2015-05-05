package fr.osb.deployapi.util;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Deployable types.</p>
 * <p>Artifacts types that are susceptible to be deployed.</p>
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public enum DeployableType {

    WAR,
    EAR,
    ZIP;

    /**
     * Returns if the given {@code type} is a <em>deployable</em> type.
     *
     * @param type
     *         The file type.
     * @return {@code true} if the given {@code type} is a <em>deployable</em> type, {@code false} otherwise.
     */
    public static boolean isDeployableType(final String type) {
        try {

            return DeployableType.valueOf(StringUtils.upperCase(type)) != null;

        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Returns if the given {@code name} matches the given {@code type}.
     *
     * @param name
     *         The file name.
     * @param type
     *         The deployable type.
     * @return {@code true} if the given {@code name} matches the given {@code type}, {@code false} otherwise.
     */
    public static boolean isType(final String name, final DeployableType type) {
        return StringUtils.endsWith(StringUtils.upperCase(name), type != null ? '.' + type.name() : null);
    }

    /**
     * Returns the given {@code type} corresponding deployable type instance.
     *
     * @param type
     *         The file type.
     * @return The given {@code type} corresponding deployable type instance, or {@code null} if it does not match a deployable type.
     */
    public static DeployableType fromString(final String type) {
        try {

            return DeployableType.valueOf(StringUtils.upperCase(type));

        } catch (final Exception e) {
            return null;
        }
    }
}
