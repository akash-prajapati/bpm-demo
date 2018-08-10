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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "flightNumber", "flightDate", "boarded", "checkedIn", "activeOnBooking" })
public class FlightInfo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("flightNumber")
	private String flightNumber;
	@JsonProperty("flightDate")
	private String flightDate;
	@JsonProperty("boarded")
	private Boolean boarded;
	@JsonProperty("checkedIn")
	private Boolean checkedIn;
	@JsonProperty("activeOnBooking")
	private Boolean activeOnBooking;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("flightNumber")
	public String getFlightNumber() {
		return flightNumber;
	}

	@JsonProperty("flightNumber")
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	@JsonProperty("flightDate")
	public String getFlightDate() {
		return flightDate;
	}

	@JsonProperty("flightDate")
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	@JsonProperty("boarded")
	public Boolean getBoarded() {
		return boarded;
	}

	@JsonProperty("boarded")
	public void setBoarded(Boolean boarded) {
		this.boarded = boarded;
	}

	@JsonProperty("checkedIn")
	public Boolean getCheckedIn() {
		return checkedIn;
	}

	@JsonProperty("checkedIn")
	public void setCheckedIn(Boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	@JsonProperty("activeOnBooking")
	public Boolean getActiveOnBooking() {
		return activeOnBooking;
	}

	@JsonProperty("activeOnBooking")
	public void setActiveOnBooking(Boolean activeOnBooking) {
		this.activeOnBooking = activeOnBooking;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}