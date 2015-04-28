package fr.osb.deployapi.model.base;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public abstract class AbstractModel {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
