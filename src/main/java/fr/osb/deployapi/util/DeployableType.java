package fr.osb.deployapi.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created on 30/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public enum DeployableType {

    WAR("tomcat"),
    EAR("tomcat"),
    ZIP("apache");

    private final String container;

    private DeployableType(final String container) {
        this.container = container;
    }

    public String getContainer() {
        return container;
    }

    public static boolean isDeployableType(final String type) {
        try {

            final DeployableType dType = DeployableType.valueOf(StringUtils.upperCase(type));
            return dType != null;

        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean isType(final String name, final DeployableType type) {
        return StringUtils.endsWith(StringUtils.upperCase(name), type != null ? '.' + type.name() : null);
    }

    public static DeployableType fromString(final String type) {
        try {

            return DeployableType.valueOf(StringUtils.upperCase(type));

        } catch (final Exception e) {
            return null;
        }
    }
}
