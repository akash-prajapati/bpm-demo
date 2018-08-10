
package com.easyjet.ei.commercials.claims.pojo.claims;
		
import java.io.Serializable;
import java.math.BigDecimal;
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
    "addedByAgent",
    "claimLineId",
    "firstName",
    "lastName",
    "lineType",
    "payableAmount",
    "status",
    "submittedAmount",
    "submittedCurrency",
    "submittedType",
    "submittedExpenseType",
    "uri"
})
public class ClaimLines implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("addedByAgent")
    private Boolean addedByAgent;
    @JsonProperty("claimLineId")
    private Integer claimLineId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("lineType")
    private String lineType;
    @JsonProperty("payableAmount")
    private BigDecimal payableAmount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("submittedAmount")
    private BigDecimal submittedAmount;
    @JsonProperty("submittedCurrency")
    private String submittedCurrency;
    @JsonProperty("submittedType")
    private String submittedType;
    @JsonProperty("submittedExpenseType")
    private String submittedExpenseType;
    @JsonProperty("uri")
    private String uri;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("addedByAgent")
    public Boolean getAddedByAgent() {
        return addedByAgent;
    }

    @JsonProperty("addedByAgent")
    public void setAddedByAgent(Boolean addedByAgent) {
        this.addedByAgent = addedByAgent;
    }

    @JsonProperty("claimLineId")
    public Integer getClaimLineId() {
        return claimLineId;
    }

    @JsonProperty("claimLineId")
    public void setClaimLineId(Integer claimLineId) {
        this.claimLineId = claimLineId;
    }

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

    @JsonProperty("lineType")
    public String getLineType() {
        return lineType;
    }

    @JsonProperty("lineType")
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    @JsonProperty("payableAmount")
    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    @JsonProperty("payableAmount")
    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("submittedAmount")
    public BigDecimal getSubmittedAmount() {
        return submittedAmount;
    }

    @JsonProperty("submittedAmount")
    public void setSubmittedAmount(BigDecimal submittedAmount) {
        this.submittedAmount = submittedAmount;
    }

    @JsonProperty("submittedCurrency")
    public String getSubmittedCurrency() {
        return submittedCurrency;
    }

    @JsonProperty("submittedCurrency")
    public void setSubmittedCurrency(String submittedCurrency) {
        this.submittedCurrency = submittedCurrency;
    }

    @JsonProperty("submittedType")
    public String getSubmittedType() {
        return submittedType;
    }

    @JsonProperty("submittedType")
    public void setSubmittedType(String submittedType) {
        this.submittedType = submittedType;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
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
        return new HashCodeBuilder().append(addedByAgent).append(claimLineId).append(firstName).append(lastName).append(lineType).append(payableAmount).append(status).append(submittedAmount).append(submittedCurrency).append(submittedType).append(uri).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClaimLines) == false) {
            return false;
        }
        ClaimLines rhs = ((ClaimLines) other);
        return new EqualsBuilder().append(addedByAgent, rhs.addedByAgent).append(claimLineId, rhs.claimLineId).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(lineType, rhs.lineType).append(payableAmount, rhs.payableAmount).append(status, rhs.status).append(submittedAmount, rhs.submittedAmount).append(submittedCurrency, rhs.submittedCurrency).append(submittedType, rhs.submittedType).append(uri, rhs.uri).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

	public String getSubmittedExpenseType() {
		return submittedExpenseType;
	}

	public void setSubmittedExpenseType(String submittedExpenseType) {
		this.submittedExpenseType = submittedExpenseType;
	}

}
