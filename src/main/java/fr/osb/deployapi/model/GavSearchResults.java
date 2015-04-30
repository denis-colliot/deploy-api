package fr.osb.deployapi.model;

import fr.osb.deployapi.model.base.AbstractModel;
import fr.osb.deployapi.model.base.ModelWithUri;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class GavSearchResults extends AbstractModel {

    private List<ModelWithUri> results;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("results", results);
    }

    public List<ModelWithUri> getResults() {
        return results;
    }

    public void setResults(List<ModelWithUri> results) {
        this.results = results;
    }
}
