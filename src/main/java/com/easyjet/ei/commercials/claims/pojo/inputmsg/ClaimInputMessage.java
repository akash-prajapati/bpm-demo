
package com.easyjet.ei.commercials.claims.pojo.inputmsg;

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
    "bankInformation",
    "claimReference",
    "decision",
    "messageFrom",
    "reason",
    "stage"
})

public class ClaimInputMessage implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("bankInformation")
    private BankInformation bankInformation;
    @JsonProperty("claimReference")
    private String claimReference;
    @JsonProperty("decision")
    private String decision;
    @JsonProperty("messageFrom")
    private String messageFrom;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("stage")
    private String stage;
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

    @JsonProperty("claimReference")
    public String getClaimReference() {
        return claimReference;
    }

    @JsonProperty("claimReference")
    public void setClaimReference(String claimReference) {
        this.claimReference = claimReference;
    }

    @JsonProperty("decision")
    public String getDecision() {
        return decision;
    }

    @JsonProperty("decision")
    public void setDecision(String decision) {
        this.decision = decision;
    }

    @JsonProperty("messageFrom")
    public String getMessageFrom() {
        return messageFrom;
    }

    @JsonProperty("messageFrom")
    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }

    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
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
        return new HashCodeBuilder().append(bankInformation).append(claimReference).append(decision).append(messageFrom).append(reason).append(stage).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimInputMessage) == false) {
            return false;
        }
        ClaimInputMessage rhs = ((ClaimInputMessage) other);
        return new EqualsBuilder().append(bankInformation, rhs.bankInformation).append(claimReference, rhs.claimReference).append(decision, rhs.decision).append(messageFrom, rhs.messageFrom).append(reason, rhs.reason).append(stage, rhs.stage).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
