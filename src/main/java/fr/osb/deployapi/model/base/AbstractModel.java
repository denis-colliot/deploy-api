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

    /**
     * <p>Appends the necessary properties to the given {@code builder}.
     * Avoid appending large data or collection.</p>
     * <p>Use it this way:</p>
     * <pre>
     * builder.append("property name", property);
     * builder.append("other property name", otherProperty);
     * </pre>
     *
     * @param builder
     *         The {@code toString} builder, never {@code null}.
     */
    protected void appendToString(final ToStringBuilder builder) {
        // Default implementation does nothing.
    }

}
