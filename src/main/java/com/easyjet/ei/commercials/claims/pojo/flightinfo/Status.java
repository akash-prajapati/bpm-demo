
package com.easyjet.ei.commercials.claims.pojo.flightinfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "aid",
    "details",
    "id",
    "info",
    "summary",
    "updateCount"
})
public class Status implements Serializable{

    /**
     * The aid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String aid = "0";
    /**
     * The details schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("details")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String details = "Flight is been departed on scheduled time. Please continue to monitor the airport screens and check Flight Tracker for updates.";
    /**
     * The id schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String id = "4";
    /**
     * The info schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("info")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String info = "Departed";
    /**
     * The summary schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("summary")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String summary = "Departed 09:45";
    /**
     * The updatecount schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("updateCount")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String updateCount = "1";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The aid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aid")
    public String getAid() {
        return aid;
    }

    /**
     * The aid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aid")
    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     * The details schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    /**
     * The details schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("details")
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * The id schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The id schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The info schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("info")
    public String getInfo() {
        return info;
    }

    /**
     * The info schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("info")
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * The summary schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * The summary schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * The updatecount schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("updateCount")
    public String getUpdateCount() {
        return updateCount;
    }

    /**
     * The updatecount schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("updateCount")
    public void setUpdateCount(String updateCount) {
        this.updateCount = updateCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aid).append(details).append(id).append(info).append(summary).append(updateCount).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Status) == false) {
            return false;
        }
        Status rhs = ((Status) other);
        return new EqualsBuilder().append(aid, rhs.aid).append(details, rhs.details).append(id, rhs.id).append(info, rhs.info).append(summary, rhs.summary).append(updateCount, rhs.updateCount).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
