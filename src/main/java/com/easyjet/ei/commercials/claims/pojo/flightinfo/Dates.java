
package com.easyjet.ei.commercials.claims.pojo.flightinfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "actualDateAndTime",
    "estimatedDateAndTime",
    "scheduledDateAndTime"
})
public class Dates implements Serializable{

    @JsonProperty("actualDateAndTime")
    private ActualDateAndTime actualDateAndTime;
    @JsonProperty("estimatedDateAndTime")
    private EstimatedDateAndTime estimatedDateAndTime;
    @JsonProperty("scheduledDateAndTime")
    private ScheduledDateAndTime scheduledDateAndTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("actualDateAndTime")
    public ActualDateAndTime getActualDateAndTime() {
        return actualDateAndTime;
    }

    @JsonProperty("actualDateAndTime")
    public void setActualDateAndTime(ActualDateAndTime actualDateAndTime) {
        this.actualDateAndTime = actualDateAndTime;
    }

    @JsonProperty("estimatedDateAndTime")
    public EstimatedDateAndTime getEstimatedDateAndTime() {
        return estimatedDateAndTime;
    }

    @JsonProperty("estimatedDateAndTime")
    public void setEstimatedDateAndTime(EstimatedDateAndTime estimatedDateAndTime) {
        this.estimatedDateAndTime = estimatedDateAndTime;
    }

    @JsonProperty("scheduledDateAndTime")
    public ScheduledDateAndTime getScheduledDateAndTime() {
        return scheduledDateAndTime;
    }

    @JsonProperty("scheduledDateAndTime")
    public void setScheduledDateAndTime(ScheduledDateAndTime scheduledDateAndTime) {
        this.scheduledDateAndTime = scheduledDateAndTime;
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
        return new HashCodeBuilder().append(actualDateAndTime).append(estimatedDateAndTime).append(scheduledDateAndTime).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Dates) == false) {
            return false;
        }
        Dates rhs = ((Dates) other);
        return new EqualsBuilder().append(actualDateAndTime, rhs.actualDateAndTime).append(estimatedDateAndTime, rhs.estimatedDateAndTime).append(scheduledDateAndTime, rhs.scheduledDateAndTime).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
