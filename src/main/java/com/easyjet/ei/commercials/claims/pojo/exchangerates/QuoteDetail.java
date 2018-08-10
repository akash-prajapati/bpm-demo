
package com.easyjet.ei.commercials.claims.pojo.exchangerates;

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
    "expiryDate",
    "merchantId",
    "quoteId",
    "roundFactor",
    "targetCurrency",
    "value"
})
public class QuoteDetail implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("expiryDate")
    private String expiryDate;
    @JsonProperty("merchantId")
    private String merchantId;
    @JsonProperty("quoteId")
    private String quoteId;
    @JsonProperty("roundFactor")
    private String roundFactor;
    @JsonProperty("targetCurrency")
    private String targetCurrency;
    @JsonProperty("value")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    @JsonProperty("expiryDate")
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @JsonProperty("merchantId")
    public String getMerchantId() {
        return merchantId;
    }

    @JsonProperty("merchantId")
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @JsonProperty("quoteId")
    public String getQuoteId() {
        return quoteId;
    }

    @JsonProperty("quoteId")
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    @JsonProperty("roundFactor")
    public String getRoundFactor() {
        return roundFactor;
    }

    @JsonProperty("roundFactor")
    public void setRoundFactor(String roundFactor) {
        this.roundFactor = roundFactor;
    }

    @JsonProperty("targetCurrency")
    public String getTargetCurrency() {
        return targetCurrency;
    }

    @JsonProperty("targetCurrency")
    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
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
        return new HashCodeBuilder().append(expiryDate).append(merchantId).append(quoteId).append(roundFactor).append(targetCurrency).append(value).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof QuoteDetail) == false) {
            return false;
        }
        QuoteDetail rhs = ((QuoteDetail) other);
        return new EqualsBuilder().append(expiryDate, rhs.expiryDate).append(merchantId, rhs.merchantId).append(quoteId, rhs.quoteId).append(roundFactor, rhs.roundFactor).append(targetCurrency, rhs.targetCurrency).append(value, rhs.value).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
