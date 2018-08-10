
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
    "arrival",
    "arrivalUTC",
    "departure",
    "departureUTC"
})
public class ActualDateAndTime implements Serializable{

    /**
     * The arrival schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrival")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object arrival = null;
    /**
     * The arrivalutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrivalUTC")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object arrivalUTC = null;
    /**
     * The departure schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departure")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object departure = null;
    /**
     * The departureutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departureUTC")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object departureUTC = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The arrival schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrival")
    public Object getArrival() {
        return arrival;
    }

    /**
     * The arrival schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrival")
    public void setArrival(Object arrival) {
        this.arrival = arrival;
    }

    /**
     * The arrivalutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrivalUTC")
    public Object getArrivalUTC() {
        return arrivalUTC;
    }

    /**
     * The arrivalutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("arrivalUTC")
    public void setArrivalUTC(Object arrivalUTC) {
        this.arrivalUTC = arrivalUTC;
    }

    /**
     * The departure schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departure")
    public Object getDeparture() {
        return departure;
    }

    /**
     * The departure schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departure")
    public void setDeparture(Object departure) {
        this.departure = departure;
    }

    /**
     * The departureutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departureUTC")
    public Object getDepartureUTC() {
        return departureUTC;
    }

    /**
     * The departureutc schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("departureUTC")
    public void setDepartureUTC(Object departureUTC) {
        this.departureUTC = departureUTC;
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
        return new HashCodeBuilder().append(arrival).append(arrivalUTC).append(departure).append(departureUTC).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActualDateAndTime) == false) {
            return false;
        }
        ActualDateAndTime rhs = ((ActualDateAndTime) other);
        return new EqualsBuilder().append(arrival, rhs.arrival).append(arrivalUTC, rhs.arrivalUTC).append(departure, rhs.departure).append(departureUTC, rhs.departureUTC).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
