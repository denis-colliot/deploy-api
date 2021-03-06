package fr.osb.deployapi.repository.artifactory.mapping;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class BuildsNumbers extends ModelWithUri {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildsNumbers.class);

    private List<BuildNumber> buildsNumbers;

    /**
     * Finds the latest build number.
     *
     * @return The latest build number.
     */
    public BuildNumber getLatest() {
        if (CollectionUtils.isEmpty(buildsNumbers)) {
            return null;
        }

        Collections.sort(buildsNumbers);

        return buildsNumbers.get(0);
    }

    public List<BuildNumber> getBuildsNumbers() {
        return buildsNumbers;
    }

    public void setBuildsNumbers(List<BuildNumber> buildsNumbers) {
        this.buildsNumbers = buildsNumbers;
    }

    /**
     * Build number.
     */
    public static class BuildNumber extends ModelWithUri implements Comparable<BuildNumber> {

        private Integer number;

        public BuildNumber() {
            // Required empty constructor.
        }

        public BuildNumber(final String uri) {
            setUri(uri);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(final BuildNumber o) {
            if (o == null) {
                return 1;
            }
            return o.getNumber().compareTo(getNumber());
        }

        @Override
        public void setUri(String uri) {
            super.setUri(uri);

            try {

                number = Integer.parseInt(StringUtils.replace(getUri(), "/", ""));

            } catch (NumberFormatException e) {
                LOGGER.trace("Invalid build number URI: '" + getUri() + "'.", e);
            }
        }

        public Integer getNumber() {
            return number;
        }

    }

}
