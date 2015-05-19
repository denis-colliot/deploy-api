package fr.osb.deployapi.repository.artifactory.mapping;

import fr.osb.deployapi.repository.IsArtifactInfo;
import fr.osb.deployapi.util.DeployableType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class FileInfo extends ModelWithUri implements IsArtifactInfo {

    private String repo;

    private String downloadUri;

    private String mimeType;

    private DeployableType deployableType;

    private Long size;

    private Date created;

    private Date lastModified;

    private Date lastUpdated;

    private Checksums checksums;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendToString(final ToStringBuilder builder) {
        builder.append("repo", repo);
        builder.append("downloadUri", downloadUri);
        builder.append("mimeType", mimeType);
        builder.append("size", size);
        builder.append("created", created);
        builder.append("lastModified", lastModified);
        builder.append("lastUpdated", lastUpdated);
        builder.append("checksums", checksums);
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public DeployableType getDeployableType() {
        return deployableType;
    }

    public void setDeployableType(DeployableType deployableType) {
        this.deployableType = deployableType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Checksums getChecksums() {
        return checksums;
    }

    public void setChecksums(Checksums checksums) {
        this.checksums = checksums;
    }

    /**
     * File info checksums structure.
     */
    public static class Checksums extends AbstractModel {

        private String md5;

        private String sha1;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void appendToString(final ToStringBuilder builder) {
            builder.append("md5", md5);
            builder.append("sha1", sha1);
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }
    }

}
