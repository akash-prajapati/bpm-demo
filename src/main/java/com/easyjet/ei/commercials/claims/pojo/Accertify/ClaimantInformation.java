
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
    "addressCountry",
    "addressCountyState",
    "addressLine1",
    "addressLine2",
    "addressPostZipCode",
    "addressTown",
    "email",
    "firstName",
    "language",
    "lastName",
    "originalBooker",
    "phoneNumber",
    "travelAgentCompanyName"
})
public class ClaimantInformation implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("addressCountry")
    private String addressCountry;
    @JsonProperty("addressCountyState")
    private String addressCountyState;
    @JsonProperty("addressLine1")
    private String addressLine1;
    @JsonProperty("addressLine2")
    private String addressLine2;
    @JsonProperty("addressPostZipCode")
    private String addressPostZipCode;
    @JsonProperty("addressTown")
    private String addressTown;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("language")
    private String language;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("originalBooker")
    private String originalBooker;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("travelAgentCompanyName")
    private String travelAgentCompanyName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("addressCountry")
    public String getAddressCountry() {
        return addressCountry;
    }

    @JsonProperty("addressCountry")
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    @JsonProperty("addressCountyState")
    public String getAddressCountyState() {
        return addressCountyState;
    }

    @JsonProperty("addressCountyState")
    public void setAddressCountyState(String addressCountyState) {
        this.addressCountyState = addressCountyState;
    }

    @JsonProperty("addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    @JsonProperty("addressLine1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @JsonProperty("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    @JsonProperty("addressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty("addressPostZipCode")
    public String getAddressPostZipCode() {
        return addressPostZipCode;
    }

    @JsonProperty("addressPostZipCode")
    public void setAddressPostZipCode(String addressPostZipCode) {
        this.addressPostZipCode = addressPostZipCode;
    }

    @JsonProperty("addressTown")
    public String getAddressTown() {
        return addressTown;
    }

    @JsonProperty("addressTown")
    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("originalBooker")
    public String getOriginalBooker() {
        return originalBooker;
    }

    @JsonProperty("originalBooker")
    public void setOriginalBooker(String originalBooker) {
        this.originalBooker = originalBooker;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("travelAgentCompanyName")
    public String getTravelAgentCompanyName() {
        return travelAgentCompanyName;
    }

    @JsonProperty("travelAgentCompanyName")
    public void setTravelAgentCompanyName(String travelAgentCompanyName) {
        this.travelAgentCompanyName = travelAgentCompanyName;
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
        return new HashCodeBuilder().append(addressCountry).append(addressCountyState).append(addressLine1).append(addressLine2).append(addressPostZipCode).append(addressTown).append(email).append(firstName).append(language).append(lastName).append(originalBooker).append(phoneNumber).append(travelAgentCompanyName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimantInformation) == false) {
            return false;
        }
        ClaimantInformation rhs = ((ClaimantInformation) other);
        return new EqualsBuilder().append(addressCountry, rhs.addressCountry).append(addressCountyState, rhs.addressCountyState).append(addressLine1, rhs.addressLine1).append(addressLine2, rhs.addressLine2).append(addressPostZipCode, rhs.addressPostZipCode).append(addressTown, rhs.addressTown).append(email, rhs.email).append(firstName, rhs.firstName).append(language, rhs.language).append(lastName, rhs.lastName).append(originalBooker, rhs.originalBooker).append(phoneNumber, rhs.phoneNumber).append(travelAgentCompanyName, rhs.travelAgentCompanyName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
