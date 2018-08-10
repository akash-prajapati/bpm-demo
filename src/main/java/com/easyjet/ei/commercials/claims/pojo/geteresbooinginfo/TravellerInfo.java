
package com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo;

import java.io.Serializable;
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
    "firstName",
    "lastName",
    "isInfant",
    "flightInfo"
})
public class TravellerInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("isInfant")
    private Boolean isInfant;
    @JsonProperty("flightInfo")
    private List<FlightInfo> flightInfo = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return new HashCodeBuilder().append(firstName).append(lastName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TravellerInfo) == false) {
            return false;
        }
        TravellerInfo rhs = ((TravellerInfo) other);
        return new EqualsBuilder().append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

	public Boolean getIsInfant() {
		return isInfant;
	}

	public void setIsInfant(Boolean isInfant) {
		this.isInfant = isInfant;
	}

	public List<FlightInfo> getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(List<FlightInfo> flightInfo) {
		this.flightInfo = flightInfo;
	}

}
