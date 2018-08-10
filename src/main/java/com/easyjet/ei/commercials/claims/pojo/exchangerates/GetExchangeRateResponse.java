
package com.easyjet.ei.commercials.claims.pojo.exchangerates;

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
    "baseCurrency",
    "quoteDetails"
})
public class GetExchangeRateResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("baseCurrency")
    private String baseCurrency;
    @JsonProperty("quoteDetails")
    private List<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("baseCurrency")
    public String getBaseCurrency() {
        return baseCurrency;
    }

    @JsonProperty("baseCurrency")
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @JsonProperty("quoteDetails")
    public List<QuoteDetail> getQuoteDetails() {
        return quoteDetails;
    }

    @JsonProperty("quoteDetails")
    public void setQuoteDetails(List<QuoteDetail> quoteDetails) {
        this.quoteDetails = quoteDetails;
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
        return new HashCodeBuilder().append(baseCurrency).append(quoteDetails).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetExchangeRateResponse) == false) {
            return false;
        }
        GetExchangeRateResponse rhs = ((GetExchangeRateResponse) other);
        return new EqualsBuilder().append(baseCurrency, rhs.baseCurrency).append(quoteDetails, rhs.quoteDetails).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
