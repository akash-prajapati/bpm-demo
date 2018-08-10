
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
    "transaction-results"
})
public class AccertifySyncResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("transaction-results")
    private TransactionResults transactionResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transaction-results")
    public TransactionResults getTransactionResults() {
        return transactionResults;
    }

    @JsonProperty("transaction-results")
    public void setTransactionResults(TransactionResults transactionResults) {
        this.transactionResults = transactionResults;
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
        return new HashCodeBuilder().append(transactionResults).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AccertifySyncResponse) == false) {
            return false;
        }
        AccertifySyncResponse rhs = ((AccertifySyncResponse) other);
        return new EqualsBuilder().append(transactionResults, rhs.transactionResults).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
