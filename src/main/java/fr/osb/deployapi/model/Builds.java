package fr.osb.deployapi.model;

import fr.osb.deployapi.model.base.AbstractModel;

import java.util.List;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class Builds extends AbstractModel {

    private List<Build> builds;

    public List<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    public static class Build extends AbstractModel {

        // No specific attribute.

    }
}
