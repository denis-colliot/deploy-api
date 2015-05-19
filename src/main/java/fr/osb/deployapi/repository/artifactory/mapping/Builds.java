package fr.osb.deployapi.repository.artifactory.mapping;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class Builds extends ModelWithUri {

    private List<ModelWithUri> builds;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("builds", builds);
    }

    public List<ModelWithUri> getBuilds() {
        return builds;
    }

    public void setBuilds(List<ModelWithUri> builds) {
        this.builds = builds;
    }

}
