
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
    "delay",
    "disruptionRecordType",
    "payAirportTransfer",
    "payAlternativeTravel",
    "payHotel",
    "payMeal",
    "primaryCauseCode",
    "secondaryCauseCode"
})
public class DisruptionDetails implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The delay schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("delay")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String delay;
    /**
     * The disruptionrecordtype schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("disruptionRecordType")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String disruptionRecordType;
    /**
     * The payairporttransfer schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAirportTransfer")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean payAirportTransfer;
    /**
     * The payalternativetravel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAlternativeTravel")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean payAlternativeTravel;
    /**
     * The payhotel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payHotel")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean payHotel;
    /**
     * The paymeal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payMeal")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean payMeal;
    /**
     * The primarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("primaryCauseCode")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String primaryCauseCode = "Weather";
    /**
     * The secondarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("secondaryCauseCode")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String secondaryCauseCode = "Bad weather condition. Heavy rainfall";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The delay schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("delay")
    public String getDelay() {
        return delay;
    }

    /**
     * The delay schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("delay")
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * The disruptionrecordtype schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("disruptionRecordType")
    public String getDisruptionRecordType() {
        return disruptionRecordType;
    }

    /**
     * The disruptionrecordtype schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("disruptionRecordType")
    public void setDisruptionRecordType(String disruptionRecordType) {
        this.disruptionRecordType = disruptionRecordType;
    }

    /**
     * The payairporttransfer schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAirportTransfer")
    public Boolean getPayAirportTransfer() {
        return payAirportTransfer;
    }

    /**
     * The payairporttransfer schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAirportTransfer")
    public void setPayAirportTransfer(Boolean payAirportTransfer) {
        this.payAirportTransfer = payAirportTransfer;
    }

    /**
     * The payalternativetravel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAlternativeTravel")
    public Boolean getPayAlternativeTravel() {
        return payAlternativeTravel;
    }

    /**
     * The payalternativetravel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payAlternativeTravel")
    public void setPayAlternativeTravel(Boolean payAlternativeTravel) {
        this.payAlternativeTravel = payAlternativeTravel;
    }

    /**
     * The payhotel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payHotel")
    public Boolean getPayHotel() {
        return payHotel;
    }

    /**
     * The payhotel schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payHotel")
    public void setPayHotel(Boolean payHotel) {
        this.payHotel = payHotel;
    }

    /**
     * The paymeal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payMeal")
    public Boolean getPayMeal() {
        return payMeal;
    }

    /**
     * The paymeal schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("payMeal")
    public void setPayMeal(Boolean payMeal) {
        this.payMeal = payMeal;
    }

    /**
     * The primarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("primaryCauseCode")
    public String getPrimaryCauseCode() {
        return primaryCauseCode;
    }

    /**
     * The primarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("primaryCauseCode")
    public void setPrimaryCauseCode(String primaryCauseCode) {
        this.primaryCauseCode = primaryCauseCode;
    }

    /**
     * The secondarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("secondaryCauseCode")
    public String getSecondaryCauseCode() {
        return secondaryCauseCode;
    }

    /**
     * The secondarycausecode schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("secondaryCauseCode")
    public void setSecondaryCauseCode(String secondaryCauseCode) {
        this.secondaryCauseCode = secondaryCauseCode;
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
        return new HashCodeBuilder().append(delay).append(disruptionRecordType).append(payAirportTransfer).append(payAlternativeTravel).append(payHotel).append(payMeal).append(primaryCauseCode).append(secondaryCauseCode).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DisruptionDetails) == false) {
            return false;
        }
        DisruptionDetails rhs = ((DisruptionDetails) other);
        return new EqualsBuilder().append(delay, rhs.delay).append(disruptionRecordType, rhs.disruptionRecordType).append(payAirportTransfer, rhs.payAirportTransfer).append(payAlternativeTravel, rhs.payAlternativeTravel).append(payHotel, rhs.payHotel).append(payMeal, rhs.payMeal).append(primaryCauseCode, rhs.primaryCauseCode).append(secondaryCauseCode, rhs.secondaryCauseCode).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
