package fr.osb.deployapi.repository.artifactory.mapping;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Collection;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
abstract class AbstractModel {

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, getExcludeFieldNames());
    }

    /**
     * Returns the field names to exclude from {@code toString()} representation.
     *
     * @return The field names to exclude from {@code toString()} representation.
     */
    protected Collection<String> getExcludeFieldNames() {
        return null;
    }

}
