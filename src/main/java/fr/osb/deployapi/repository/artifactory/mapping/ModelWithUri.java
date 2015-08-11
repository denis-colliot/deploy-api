package fr.osb.deployapi.repository.artifactory.mapping;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
// Should not be abstract.
public class ModelWithUri extends AbstractModel {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
