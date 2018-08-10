
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
    "disruptionDetails"
})
public class PostFlight implements Serializable{

    @JsonProperty("disruptionDetails")
    private DisruptionDetails disruptionDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("disruptionDetails")
    public DisruptionDetails getDisruptionDetails() {
        return disruptionDetails;
    }

    @JsonProperty("disruptionDetails")
    public void setDisruptionDetails(DisruptionDetails disruptionDetails) {
        this.disruptionDetails = disruptionDetails;
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
        return new HashCodeBuilder().append(disruptionDetails).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PostFlight) == false) {
            return false;
        }
        PostFlight rhs = ((PostFlight) other);
        return new EqualsBuilder().append(disruptionDetails, rhs.disruptionDetails).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
