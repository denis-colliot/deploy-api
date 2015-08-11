package fr.osb.deployapi.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Maven "<b>G</b>roup - <b>A</b>rtifact - <b>V</b>ersion" corresponding java bean.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public final class Gav {

    private String group;

    private String artifact;

    private String version;

    public Gav(final String value, final String delimiter) {
        final String[] split = StringUtils.split(value, delimiter);
        if (split.length < 3) {
            throw new IllegalArgumentException("Missing information in GAV value.");
        }
        group = split[0];
        artifact = split[1];
        version = split[2];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gav gav = (Gav) o;

        if (artifact != null ? !artifact.equals(gav.artifact) : gav.artifact != null) return false;
        if (group != null ? !group.equals(gav.group) : gav.group != null) return false;
        if (version != null ? !version.equals(gav.version) : gav.version != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (artifact != null ? artifact.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getVersion() {
        return version;
    }
}
