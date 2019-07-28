
package org.cyfer.gosearch.searchresponse;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contentStreamId",
    "archivePath",
    "length",
    "mimeType",
    "fileName",
    "digest",
    "repositoryId"
})
public class ContentStream {

    @JsonProperty("contentStreamId")
    private String contentStreamId;
    @JsonProperty("archivePath")
    private String archivePath;
    @JsonProperty("length")
    private Integer length;
    @JsonProperty("mimeType")
    private String mimeType;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("digest")
    private String digest;
    @JsonProperty("repositoryId")
    private String repositoryId;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("contentStreamId")
    public String getContentStreamId() {
        return contentStreamId;
    }

    @JsonProperty("contentStreamId")
    public void setContentStreamId(String contentStreamId) {
        this.contentStreamId = contentStreamId;
    }

    @JsonProperty("archivePath")
    public String getArchivePath() {
        return archivePath;
    }

    @JsonProperty("archivePath")
    public void setArchivePath(String archivePath) {
        this.archivePath = archivePath;
    }

    @JsonProperty("length")
    public Integer getLength() {
        return length;
    }

    @JsonProperty("length")
    public void setLength(Integer length) {
        this.length = length;
    }

    @JsonProperty("mimeType")
    public String getMimeType() {
        return mimeType;
    }

    @JsonProperty("mimeType")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty("digest")
    public String getDigest() {
        return digest;
    }

    @JsonProperty("digest")
    public void setDigest(String digest) {
        this.digest = digest;
    }

    @JsonProperty("repositoryId")
    public String getRepositoryId() {
        return repositoryId;
    }

    @JsonProperty("repositoryId")
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    @JsonAnyGetter
    public Map<String, java.lang.Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, java.lang.Object value) {
        this.additionalProperties.put(name, value);
    }

}
