package fr.osb.deployapi.repository.artifactory.mapping;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
// Should not be abstract.
public class ModelWithUri extends AbstractModel {

    private String uri;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("uri", uri);
        appendToString(builder);
        return builder.toString();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
