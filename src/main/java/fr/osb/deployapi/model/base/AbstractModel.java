package fr.osb.deployapi.model.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public abstract class AbstractModel {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        appendToString(builder);
        return builder.toString();
    }

    protected void appendToString(final ToStringBuilder builder) {
        // Default implementation does nothing.
    }

}
