
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
    "bankAcctCountry",
    "bankAcctName",
    "bankAcctValidated",
    "intBankBicSwift",
    "intBankIban",
    "ukBankAccountNo",
    "ukBankSortCode"
})
public class BankInformation implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @JsonProperty("bankAcctCountry")
    private String bankAcctCountry;
    @JsonProperty("bankAcctName")
    private String bankAcctName;
    @JsonProperty("bankAcctValidated")
    private String bankAcctValidated;
    @JsonProperty("intBankBicSwift")
    private String intBankBicSwift;
    @JsonProperty("intBankIban")
    private String intBankIban;
    @JsonProperty("ukBankAccountNo")
    private String ukBankAccountNo;
    @JsonProperty("ukBankSortCode")
    private String ukBankSortCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bankAcctCountry")
    public String getBankAcctCountry() {
        return bankAcctCountry;
    }

    @JsonProperty("bankAcctCountry")
    public void setBankAcctCountry(String bankAcctCountry) {
        this.bankAcctCountry = bankAcctCountry;
    }

    @JsonProperty("bankAcctName")
    public String getBankAcctName() {
        return bankAcctName;
    }

    @JsonProperty("bankAcctName")
    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    @JsonProperty("bankAcctValidated")
    public String getBankAcctValidated() {
        return bankAcctValidated;
    }

    @JsonProperty("bankAcctValidated")
    public void setBankAcctValidated(String bankAcctValidated) {
        this.bankAcctValidated = bankAcctValidated;
    }

    @JsonProperty("intBankBicSwift")
    public String getIntBankBicSwift() {
        return intBankBicSwift;
    }

    @JsonProperty("intBankBicSwift")
    public void setIntBankBicSwift(String intBankBicSwift) {
        this.intBankBicSwift = intBankBicSwift;
    }

    @JsonProperty("intBankIban")
    public String getIntBankIban() {
        return intBankIban;
    }

    @JsonProperty("intBankIban")
    public void setIntBankIban(String intBankIban) {
        this.intBankIban = intBankIban;
    }

    @JsonProperty("ukBankAccountNo")
    public String getUkBankAccountNo() {
        return ukBankAccountNo;
    }

    @JsonProperty("ukBankAccountNo")
    public void setUkBankAccountNo(String ukBankAccountNo) {
        this.ukBankAccountNo = ukBankAccountNo;
    }

    @JsonProperty("ukBankSortCode")
    public String getUkBankSortCode() {
        return ukBankSortCode;
    }

    @JsonProperty("ukBankSortCode")
    public void setUkBankSortCode(String ukBankSortCode) {
        this.ukBankSortCode = ukBankSortCode;
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
        return new HashCodeBuilder().append(bankAcctCountry).append(bankAcctName).append(bankAcctValidated).append(intBankBicSwift).append(intBankIban).append(ukBankAccountNo).append(ukBankSortCode).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BankInformation) == false) {
            return false;
        }
        BankInformation rhs = ((BankInformation) other);
        return new EqualsBuilder().append(bankAcctCountry, rhs.bankAcctCountry).append(bankAcctName, rhs.bankAcctName).append(bankAcctValidated, rhs.bankAcctValidated).append(intBankBicSwift, rhs.intBankBicSwift).append(intBankIban, rhs.intBankIban).append(ukBankAccountNo, rhs.ukBankAccountNo).append(ukBankSortCode, rhs.ukBankSortCode).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
