
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
    "iata",
    "name",
    "terminal"
})
public class ActualDepartureAirport implements Serializable{

    /**
     * The iata schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("iata")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String iata = "LTN";
    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String name = "London Luton";
    /**
     * The terminal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("terminal")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object terminal = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The iata schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("iata")
    public String getIata() {
        return iata;
    }

    /**
     * The iata schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("iata")
    public void setIata(String iata) {
        this.iata = iata;
    }

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The terminal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("terminal")
    public Object getTerminal() {
        return terminal;
    }

    /**
     * The terminal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("terminal")
    public void setTerminal(Object terminal) {
        this.terminal = terminal;
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
        return new HashCodeBuilder().append(iata).append(name).append(terminal).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActualDepartureAirport) == false) {
            return false;
        }
        ActualDepartureAirport rhs = ((ActualDepartureAirport) other);
        return new EqualsBuilder().append(iata, rhs.iata).append(name, rhs.name).append(terminal, rhs.terminal).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
