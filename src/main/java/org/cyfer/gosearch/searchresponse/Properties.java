
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
    "objectId",
    "baseTypeId",
    "objectTypeId",
    "createdBy",
    "creationDate",
    "lastModifiedBy",
    "lastModificationDate",
    "versionNumber",
    "tenant",
    "traceId",
    "Name"
})
public class Properties {

    @JsonProperty("objectId")
    private ObjectId objectId;
    @JsonProperty("baseTypeId")
    private BaseTypeId baseTypeID;
    @JsonProperty("objectTypeId")
    private ObjectTypeId objectTypeId;
    @JsonProperty("createdBy")
    private CreatedBy createdBy;
    @JsonProperty("creationDate")
    private CreationDate creationDate;
    @JsonProperty("lastModifiedBy")
    private LastModifiedBy lastModifiedBy;
    @JsonProperty("lastModificationDate")
    private LastModificationDate lastModificationDate;
    @JsonProperty("versionNumber")
    private VersionNumber versionNumber;
    @JsonProperty("tenant")
    private Tenant tenant;
    @JsonProperty("traceId")
    private TraceId traceId;
    @JsonProperty("Name")
    private Name name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("weight")
    private double weight;

    @JsonProperty("location")
    private String location;

    @JsonProperty("username")
    private String username;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("image")
    private String image;

    public String getImage() {
        return image;
    }

    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("objectId")
    public ObjectId getObjectId() {
        return objectId;
    }

    @JsonProperty("objectId")
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    @JsonProperty("baseTypeId")
    public BaseTypeId getBaseTypeID() {
        return baseTypeID;
    }

    @JsonProperty("baseTypeId")
    public void setBaseTypeID(BaseTypeId baseTypeID) {
        this.baseTypeID = baseTypeID;
    }

    @JsonProperty("objectTypeId")
    public ObjectTypeId getObjectTypeId() {
        return objectTypeId;
    }

    @JsonProperty("objectTypeId")
    public void setObjectTypeId(ObjectTypeId objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    @JsonProperty("createdBy")
    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("creationDate")
    public CreationDate getCreationDate() {
        return creationDate;
    }

    @JsonProperty("creationDate")
    public void setCreationDate(CreationDate creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("lastModifiedBy")
    public LastModifiedBy getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(LastModifiedBy lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty("lastModificationDate")
    public LastModificationDate getLastModificationDate() {
        return lastModificationDate;
    }

    @JsonProperty("lastModificationDate")
    public void setLastModificationDate(LastModificationDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @JsonProperty("versionNumber")
    public VersionNumber getVersionNumber() {
        return versionNumber;
    }

    @JsonProperty("versionNumber")
    public void setVersionNumber(VersionNumber versionNumber) {
        this.versionNumber = versionNumber;
    }

    @JsonProperty("tenant")
    public Tenant getTenant() {
        return tenant;
    }

    @JsonProperty("tenant")
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @JsonProperty("traceId")
    public TraceId getTraceId() {
        return traceId;
    }

    @JsonProperty("traceId")
    public void setTraceId(TraceId traceId) {
        this.traceId = traceId;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public String getLocation() {
        return location;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("Name")
    public Name getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(Name name) {
        this.name = name;
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
