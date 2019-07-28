
package org.cyfer.gosearch.searchresponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "objects",
    "numItems",
    "hasMoreItems",
    "totalNumItems"
})
public class SearchResponse {

    @JsonProperty("objects")
    private List<StoredObject> storedObjects = null;
    @JsonProperty("numItems")
    private Integer numItems;
    @JsonProperty("hasMoreItems")
    private Boolean hasMoreItems;
    @JsonProperty("totalNumItems")
    private Integer totalNumItems;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("objects")
    public List<StoredObject> getStoredObjects() {
        return storedObjects;
    }

    @JsonProperty("objects")
    public void setStoredObjects(List<StoredObject> storedObjects) {
        this.storedObjects = storedObjects;
    }

    @JsonProperty("numItems")
    public Integer getNumItems() {
        return numItems;
    }

    @JsonProperty("numItems")
    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
    }

    @JsonProperty("hasMoreItems")
    public Boolean getHasMoreItems() {
        return hasMoreItems;
    }

    @JsonProperty("hasMoreItems")
    public void setHasMoreItems(Boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

    @JsonProperty("totalNumItems")
    public Integer getTotalNumItems() {
        return totalNumItems;
    }

    @JsonProperty("totalNumItems")
    public void setTotalNumItems(Integer totalNumItems) {
        this.totalNumItems = totalNumItems;
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
