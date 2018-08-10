
package com.easyjet.ei.commercials.claims.pojo.flightinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "flights",
    "summary"
})
public class CustomerFlightStatus implements Serializable {

    @JsonProperty("flights")
    private List<Flight> flights = new ArrayList<Flight>();
    @JsonProperty("summary")
    private Summary summary;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("flights")
    public List<Flight> getFlights() {
        return flights;
    }

    @JsonProperty("flights")
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @JsonProperty("summary")
    public Summary getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(Summary summary) {
        this.summary = summary;
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
        return new HashCodeBuilder().append(flights).append(summary).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomerFlightStatus) == false) {
            return false;
        }
        CustomerFlightStatus rhs = ((CustomerFlightStatus) other);
        return new EqualsBuilder().append(flights, rhs.flights).append(summary, rhs.summary).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
