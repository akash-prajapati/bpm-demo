
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
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "flightDate",
    "flightDistance",
    "flightNumber",
    "flightSegment",
    "postFlight"
})
public class Flight implements Serializable{

    /**
     * The flightdate schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDate")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String flightDate = "2017-01-01T00:00:00";
    /**
     * The flightdistance schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDistance")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String flightDistance = "1234";
    /**
     * The flightnumber schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightNumber")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String flightNumber = "EZY2151";
    @JsonProperty("flightSegment")
    private List<FlightSegment> flightSegment = new ArrayList<FlightSegment>();
    @JsonProperty("postFlight")
    private List<PostFlight> postFlight = new ArrayList<PostFlight>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The flightdate schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDate")
    public String getFlightDate() {
        return flightDate;
    }

    /**
     * The flightdate schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDate")
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * The flightdistance schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDistance")
    public String getFlightDistance() {
        return flightDistance;
    }

    /**
     * The flightdistance schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightDistance")
    public void setFlightDistance(String flightDistance) {
        this.flightDistance = flightDistance;
    }

    /**
     * The flightnumber schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightNumber")
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * The flightnumber schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightNumber")
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @JsonProperty("flightSegment")
    public List<FlightSegment> getFlightSegment() {
        return flightSegment;
    }

    @JsonProperty("flightSegment")
    public void setFlightSegment(List<FlightSegment> flightSegment) {
        this.flightSegment = flightSegment;
    }

    @JsonProperty("postFlight")
    public List<PostFlight> getPostFlight() {
        return postFlight;
    }

    @JsonProperty("postFlight")
    public void setPostFlight(List<PostFlight> postFlight) {
        this.postFlight = postFlight;
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
        return new HashCodeBuilder().append(flightDate).append(flightDistance).append(flightNumber).append(flightSegment).append(postFlight).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(flightDate, rhs.flightDate).append(flightDistance, rhs.flightDistance).append(flightNumber, rhs.flightNumber).append(flightSegment, rhs.flightSegment).append(postFlight, rhs.postFlight).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
