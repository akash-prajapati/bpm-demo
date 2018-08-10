
package com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo;

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
    "bookerInfo",
    "bookingRef",
    "flights",
    "isStaffTravel",
    "isEasyJetHolidays",
    "travellerInfo"
})
public class BookingInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("bookerInfo")
    private BookerInfo bookerInfo;
    @JsonProperty("bookingRef")
    private String bookingRef;
    @JsonProperty("flight_")
    private List<Flight> flights = new ArrayList<Flight>();
    @JsonProperty("isStaffTravel")
    private String isStaffTravel;
    
    	@JsonProperty("isEasyJetHolidays")
    private String isEasyJetHolidays;
    @JsonProperty("travellerInfo")
    private List<TravellerInfo> travellerInfo = new ArrayList<TravellerInfo>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bookerInfo")
    public BookerInfo getBookerInfo() {
        return bookerInfo;
    }

    @JsonProperty("bookerInfo")
    public void setBookerInfo(BookerInfo bookerInfo) {
        this.bookerInfo = bookerInfo;
    }

    @JsonProperty("bookingRef")
    public String getBookingRef() {
        return bookingRef;
    }

    @JsonProperty("bookingRef")
    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }

    @JsonProperty("flights")
    public List<Flight> getFlights() {
        return flights;
    }

    @JsonProperty("flights")
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @JsonProperty("isStaffTravel")
    public String getIsStaffTravel() {
        return isStaffTravel;
    }

    @JsonProperty("isStaffTravel")
    public void setIsStaffTravel(String isStaffTravel) {
        this.isStaffTravel = isStaffTravel;
    }
    
    @JsonProperty("isEasyJetHolidays")
    public String getIsEasyJetHolidays() {
		return isEasyJetHolidays;
	}

    @JsonProperty("isEasyJetHolidays")
    public void setIsEasyJetHolidays(String isEasyJetHolidays) {
		this.isEasyJetHolidays = isEasyJetHolidays;
	}



    @JsonProperty("travellerInfo")
    public List<TravellerInfo> getTravellerInfo() {
        return travellerInfo;
    }

    @JsonProperty("travellerInfo")
    public void setTravellerInfo(List<TravellerInfo> travellerInfo) {
        this.travellerInfo = travellerInfo;
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
        return new HashCodeBuilder().append(bookerInfo).append(bookingRef).append(flights).append(isStaffTravel).append(travellerInfo).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BookingInfo) == false) {
            return false;
        }
        BookingInfo rhs = ((BookingInfo) other);
        return new EqualsBuilder().append(bookerInfo, rhs.bookerInfo).append(bookingRef, rhs.bookingRef).append(flights, rhs.flights).append(isStaffTravel, rhs.isStaffTravel).append(travellerInfo, rhs.travellerInfo).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
