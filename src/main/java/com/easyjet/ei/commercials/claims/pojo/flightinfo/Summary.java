
package com.easyjet.ei.commercials.claims.pojo.flightinfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "count",
    "start",
    "total"
})
public class Summary implements Serializable{

    /**
     * The count schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("count")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String count = "3";
    /**
     * The start schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("start")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String start = "1";
    /**
     * The total schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("total")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String total = "3";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The count schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("count")
    public String getCount() {
        return count;
    }

    /**
     * The count schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("count")
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * The start schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    /**
     * The start schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * The total schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    /**
     * The total schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
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
        return new HashCodeBuilder().append(count).append(start).append(total).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Summary) == false) {
            return false;
        }
        Summary rhs = ((Summary) other);
        return new EqualsBuilder().append(count, rhs.count).append(start, rhs.start).append(total, rhs.total).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
