package fr.osb.deployapi.repository.artifactory.mapping;

import java.util.List;

/**
 * Search results model.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class SearchResults extends AbstractModel {

    private List<ModelWithUri> results;

    public List<ModelWithUri> getResults() {
        return results;
    }

    public void setResults(List<ModelWithUri> results) {
        this.results = results;
    }
}
