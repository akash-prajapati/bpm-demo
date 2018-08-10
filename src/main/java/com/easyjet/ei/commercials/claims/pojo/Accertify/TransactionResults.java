
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
    "cross-reference",
    "recommendation-code",
    "remarks",
    "rules-tripped",
    "total-score",
    "transaction-id"
})
public class TransactionResults implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("cross-reference")
    private String crossReference;
    @JsonProperty("recommendation-code")
    private String recommendationCode;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("rules-tripped")
    private String rulesTripped;
    @JsonProperty("total-score")
    private Integer totalScore;
    @JsonProperty("transaction-id")
    private String transactionId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cross-reference")
    public String getCrossReference() {
        return crossReference;
    }

    @JsonProperty("cross-reference")
    public void setCrossReference(String crossReference) {
        this.crossReference = crossReference;
    }

    @JsonProperty("recommendation-code")
    public String getRecommendationCode() {
        return recommendationCode;
    }

    @JsonProperty("recommendation-code")
    public void setRecommendationCode(String recommendationCode) {
        this.recommendationCode = recommendationCode;
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return remarks;
    }

    @JsonProperty("remarks")
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("rules-tripped")
    public String getRulesTripped() {
        return rulesTripped;
    }

    @JsonProperty("rules-tripped")
    public void setRulesTripped(String rulesTripped) {
        this.rulesTripped = rulesTripped;
    }

    @JsonProperty("total-score")
    public Integer getTotalScore() {
        return totalScore;
    }

    @JsonProperty("total-score")
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @JsonProperty("transaction-id")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transaction-id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
        return new HashCodeBuilder().append(crossReference).append(recommendationCode).append(remarks).append(rulesTripped).append(totalScore).append(transactionId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TransactionResults) == false) {
            return false;
        }
        TransactionResults rhs = ((TransactionResults) other);
        return new EqualsBuilder().append(crossReference, rhs.crossReference).append(recommendationCode, rhs.recommendationCode).append(remarks, rhs.remarks).append(rulesTripped, rhs.rulesTripped).append(totalScore, rhs.totalScore).append(transactionId, rhs.transactionId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
