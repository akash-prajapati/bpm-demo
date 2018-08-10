
package com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo;

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
    "flightDate",
    "flightNumber"
})
public class Flight implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("flightDate")
    private String flightDate;
    @JsonProperty("flightNumber")
    private String flightNumber;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("flightDate")
    public String getFlightDate() {
        return flightDate;
    }

    @JsonProperty("flightDate")
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    @JsonProperty("flightNumber")
    public String getFlightNumber() {
        return flightNumber;
    }

    @JsonProperty("flightNumber")
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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
        return new HashCodeBuilder().append(flightDate).append(flightNumber).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Flight) == false) {
            return false;
        }
        Flight rhs = ((Flight) other);
        return new EqualsBuilder().append(flightDate, rhs.flightDate).append(flightNumber, rhs.flightNumber).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
