package fr.osb.deployapi.model;

import fr.osb.deployapi.model.base.AbstractModel;
import fr.osb.deployapi.model.base.ModelWithUri;
import fr.osb.deployapi.util.Gav;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class BuildInfo extends ModelWithUri {

    private BuildInfoData buildInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("buildInfo", buildInfo);
    }

    public BuildInfoData getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(BuildInfoData buildInfo) {
        this.buildInfo = buildInfo;
    }

    /**
     * Build info data structure.
     */
    public static class BuildInfoData extends AbstractModel {

        private String type;

        private Integer number;

        private String vcsRevision;

        private List<Module> modules;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void appendToString(final ToStringBuilder builder) {
            builder.append("type", type);
            builder.append("number", number);
            builder.append("vcsRevision", vcsRevision);
            builder.append("modules", modules);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getVcsRevision() {
            return vcsRevision;
        }

        public void setVcsRevision(String vcsRevision) {
            this.vcsRevision = vcsRevision;
        }

        public List<Module> getModules() {
            return modules;
        }

        public void setModules(List<Module> modules) {
            this.modules = modules;
        }

    }

    /**
     * Build info module structure.
     */
    public static class Module extends AbstractModel {

        private String id;

        private Gav gav;

        private Map<String, String> properties;

        private List<Artifact> artifacts;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void appendToString(final ToStringBuilder builder) {
            builder.append("id", id);
            builder.append("gav", gav);
            builder.append("artifacts", artifacts);
            builder.append("properties", properties);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
            setGav(new Gav(id, ":"));
        }

        public Gav getGav() {
            return gav;
        }

        public void setGav(Gav gav) {
            this.gav = gav;
        }

        public Map<String, String> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, String> properties) {
            this.properties = properties;
        }

        public List<Artifact> getArtifacts() {
            return artifacts;
        }

        public void setArtifacts(List<Artifact> artifacts) {
            this.artifacts = artifacts;
        }
    }

    /**
     * Build info module artifact structure.
     */
    public static class Artifact extends AbstractModel {

        private String type;

        private String sha1;

        private String md5;

        private String name;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void appendToString(final ToStringBuilder builder) {
            builder.append("type", type);
            builder.append("sha1", sha1);
            builder.append("md5", md5);
            builder.append("name", name);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
