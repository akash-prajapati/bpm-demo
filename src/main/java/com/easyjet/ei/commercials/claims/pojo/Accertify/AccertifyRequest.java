
package com.easyjet.ei.commercials.claims.pojo.Accertify;

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
    "bankInformation",
    "claimDetails",
    "claimantInformation",
    "flightInformation",
    "passengerList",
    "transactionInformation",
    "welfareDetails"
})
public class AccertifyRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("bankInformation")
    private BankInformation bankInformation;
    @JsonProperty("claimDetails")
    private ClaimDetails claimDetails;
    @JsonProperty("claimantInformation")
    private ClaimantInformation claimantInformation;
    @JsonProperty("flightInformation")
    private FlightInformation flightInformation;
    @JsonProperty("passengerList")
    private List<PassengerList> passengerList = new ArrayList<PassengerList>();
    @JsonProperty("transactionInformation")
    private TransactionInformation transactionInformation;
    @JsonProperty("welfareDetails")
    private List<WelfareDetail> welfareDetails = new ArrayList<WelfareDetail>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bankInformation")
    public BankInformation getBankInformation() {
        return bankInformation;
    }

    @JsonProperty("bankInformation")
    public void setBankInformation(BankInformation bankInformation) {
        this.bankInformation = bankInformation;
    }

    @JsonProperty("claimDetails")
    public ClaimDetails getClaimDetails() {
        return claimDetails;
    }

    @JsonProperty("claimDetails")
    public void setClaimDetails(ClaimDetails claimDetails) {
        this.claimDetails = claimDetails;
    }

    @JsonProperty("claimantInformation")
    public ClaimantInformation getClaimantInformation() {
        return claimantInformation;
    }

    @JsonProperty("claimantInformation")
    public void setClaimantInformation(ClaimantInformation claimantInformation) {
        this.claimantInformation = claimantInformation;
    }

    @JsonProperty("flightInformation")
    public FlightInformation getFlightInformation() {
        return flightInformation;
    }

    @JsonProperty("flightInformation")
    public void setFlightInformation(FlightInformation flightInformation) {
        this.flightInformation = flightInformation;
    }

    @JsonProperty("passengerList")
    public List<PassengerList> getPassengerList() {
        return passengerList;
    }

    @JsonProperty("passengerList")
    public void setPassengerList(List<PassengerList> passengerList) {
        this.passengerList = passengerList;
    }

    @JsonProperty("transactionInformation")
    public TransactionInformation getTransactionInformation() {
        return transactionInformation;
    }

    @JsonProperty("transactionInformation")
    public void setTransactionInformation(TransactionInformation transactionInformation) {
        this.transactionInformation = transactionInformation;
    }

    @JsonProperty("welfareDetails")
    public List<WelfareDetail> getWelfareDetails() {
        return welfareDetails;
    }

    @JsonProperty("welfareDetails")
    public void setWelfareDetails(List<WelfareDetail> welfareDetails) {
        this.welfareDetails = welfareDetails;
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
        return new HashCodeBuilder().append(bankInformation).append(claimDetails).append(claimantInformation).append(flightInformation).append(passengerList).append(transactionInformation).append(welfareDetails).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AccertifyRequest) == false) {
            return false;
        }
        AccertifyRequest rhs = ((AccertifyRequest) other);
        return new EqualsBuilder().append(bankInformation, rhs.bankInformation).append(claimDetails, rhs.claimDetails).append(claimantInformation, rhs.claimantInformation).append(flightInformation, rhs.flightInformation).append(passengerList, rhs.passengerList).append(transactionInformation, rhs.transactionInformation).append(welfareDetails, rhs.welfareDetails).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
