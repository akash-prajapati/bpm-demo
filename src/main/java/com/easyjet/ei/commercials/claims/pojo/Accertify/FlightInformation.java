
package com.easyjet.ei.commercials.claims.pojo.Accertify;

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
    "arrivalAirport",
    "bookingRef",
    "departureAiport",
    "flightDate",
    "flightNo"
})
public class FlightInformation implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("arrivalAirport")
    private String arrivalAirport;
    @JsonProperty("bookingRef")
    private String bookingRef;
    @JsonProperty("departureAiport")
    private String departureAiport;
    @JsonProperty("flightDate")
    private String flightDate;
    @JsonProperty("flightNo")
    private String flightNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("arrivalAirport")
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    @JsonProperty("arrivalAirport")
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @JsonProperty("bookingRef")
    public String getBookingRef() {
        return bookingRef;
    }

    @JsonProperty("bookingRef")
    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }

    @JsonProperty("departureAiport")
    public String getDepartureAiport() {
        return departureAiport;
    }

    @JsonProperty("departureAiport")
    public void setDepartureAiport(String departureAiport) {
        this.departureAiport = departureAiport;
    }

    @JsonProperty("flightDate")
    public String getFlightDate() {
        return flightDate;
    }

    @JsonProperty("flightDate")
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    @JsonProperty("flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    @JsonProperty("flightNo")
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
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
        return new HashCodeBuilder().append(arrivalAirport).append(bookingRef).append(departureAiport).append(flightDate).append(flightNo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FlightInformation) == false) {
            return false;
        }
        FlightInformation rhs = ((FlightInformation) other);
        return new EqualsBuilder().append(arrivalAirport, rhs.arrivalAirport).append(bookingRef, rhs.bookingRef).append(departureAiport, rhs.departureAiport).append(flightDate, rhs.flightDate).append(flightNo, rhs.flightNo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
