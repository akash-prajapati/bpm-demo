
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
    "aircraftRegistrationId",
    "airports",
    "canCheckinOnline",
    "dates",
    "flightKey",
    "status"
})
public class FlightSegment implements Serializable{

    /**
     * The aircraftregistrationid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aircraftRegistrationId")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String aircraftRegistrationId = "RegID123";
    @JsonProperty("airports")
    private Airports airports;
    /**
     * The cancheckinonline schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("canCheckinOnline")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean canCheckinOnline = true;
    @JsonProperty("dates")
    private Dates dates;
    /**
     * The flightkey schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightKey")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String flightKey = "20170101LTNAMS2151";
    @JsonProperty("status")
    private Status status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The aircraftregistrationid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aircraftRegistrationId")
    public String getAircraftRegistrationId() {
        return aircraftRegistrationId;
    }

    /**
     * The aircraftregistrationid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("aircraftRegistrationId")
    public void setAircraftRegistrationId(String aircraftRegistrationId) {
        this.aircraftRegistrationId = aircraftRegistrationId;
    }

    @JsonProperty("airports")
    public Airports getAirports() {
        return airports;
    }

    @JsonProperty("airports")
    public void setAirports(Airports airports) {
        this.airports = airports;
    }

    /**
     * The cancheckinonline schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("canCheckinOnline")
    public Boolean getCanCheckinOnline() {
        return canCheckinOnline;
    }

    /**
     * The cancheckinonline schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("canCheckinOnline")
    public void setCanCheckinOnline(Boolean canCheckinOnline) {
        this.canCheckinOnline = canCheckinOnline;
    }

    @JsonProperty("dates")
    public Dates getDates() {
        return dates;
    }

    @JsonProperty("dates")
    public void setDates(Dates dates) {
        this.dates = dates;
    }

    /**
     * The flightkey schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightKey")
    public String getFlightKey() {
        return flightKey;
    }

    /**
     * The flightkey schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("flightKey")
    public void setFlightKey(String flightKey) {
        this.flightKey = flightKey;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
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
        return new HashCodeBuilder().append(aircraftRegistrationId).append(airports).append(canCheckinOnline).append(dates).append(flightKey).append(status).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FlightSegment) == false) {
            return false;
        }
        FlightSegment rhs = ((FlightSegment) other);
        return new EqualsBuilder().append(aircraftRegistrationId, rhs.aircraftRegistrationId).append(airports, rhs.airports).append(canCheckinOnline, rhs.canCheckinOnline).append(dates, rhs.dates).append(flightKey, rhs.flightKey).append(status, rhs.status).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
