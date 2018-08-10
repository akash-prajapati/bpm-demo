
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
    "actualArrivalAirport",
    "actualDepartureAirport",
    "scheduleArrivalAirport",
    "scheduleDepartureAirport"
})
public class Airports implements Serializable{

    @JsonProperty("actualArrivalAirport")
    private ActualArrivalAirport actualArrivalAirport;
    @JsonProperty("actualDepartureAirport")
    private ActualDepartureAirport actualDepartureAirport;
    @JsonProperty("scheduleArrivalAirport")
    private ScheduleArrivalAirport scheduleArrivalAirport;
    @JsonProperty("scheduleDepartureAirport")
    private ScheduleDepartureAirport scheduleDepartureAirport;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("actualArrivalAirport")
    public ActualArrivalAirport getActualArrivalAirport() {
        return actualArrivalAirport;
    }

    @JsonProperty("actualArrivalAirport")
    public void setActualArrivalAirport(ActualArrivalAirport actualArrivalAirport) {
        this.actualArrivalAirport = actualArrivalAirport;
    }

    @JsonProperty("actualDepartureAirport")
    public ActualDepartureAirport getActualDepartureAirport() {
        return actualDepartureAirport;
    }

    @JsonProperty("actualDepartureAirport")
    public void setActualDepartureAirport(ActualDepartureAirport actualDepartureAirport) {
        this.actualDepartureAirport = actualDepartureAirport;
    }

    @JsonProperty("scheduleArrivalAirport")
    public ScheduleArrivalAirport getScheduleArrivalAirport() {
        return scheduleArrivalAirport;
    }

    @JsonProperty("scheduleArrivalAirport")
    public void setScheduleArrivalAirport(ScheduleArrivalAirport scheduleArrivalAirport) {
        this.scheduleArrivalAirport = scheduleArrivalAirport;
    }

    @JsonProperty("scheduleDepartureAirport")
    public ScheduleDepartureAirport getScheduleDepartureAirport() {
        return scheduleDepartureAirport;
    }

    @JsonProperty("scheduleDepartureAirport")
    public void setScheduleDepartureAirport(ScheduleDepartureAirport scheduleDepartureAirport) {
        this.scheduleDepartureAirport = scheduleDepartureAirport;
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
        return new HashCodeBuilder().append(actualArrivalAirport).append(actualDepartureAirport).append(scheduleArrivalAirport).append(scheduleDepartureAirport).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Airports) == false) {
            return false;
        }
        Airports rhs = ((Airports) other);
        return new EqualsBuilder().append(actualArrivalAirport, rhs.actualArrivalAirport).append(actualDepartureAirport, rhs.actualDepartureAirport).append(scheduleArrivalAirport, rhs.scheduleArrivalAirport).append(scheduleDepartureAirport, rhs.scheduleDepartureAirport).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
