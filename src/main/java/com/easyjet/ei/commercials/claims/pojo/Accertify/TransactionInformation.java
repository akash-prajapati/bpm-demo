
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
    "claimReference"
	//,    "ipAddress"
})
public class TransactionInformation implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("claimReference")
    private String claimReference;
   /* @JsonProperty("ipAddress")
    private String ipAddress;*/
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("claimReference")
    public String getClaimReference() {
        return claimReference;
    }

    @JsonProperty("claimReference")
    public void setClaimReference(String claimReference) {
        this.claimReference = claimReference;
    }

  /*  @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }*/

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
        return new HashCodeBuilder().append(claimReference).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TransactionInformation) == false) {
            return false;
        }
        TransactionInformation rhs = ((TransactionInformation) other);
        return new EqualsBuilder().append(claimReference, rhs.claimReference).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
