
package com.easyjet.ei.commercial.claims.pojo.getdocumentfroms3;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
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
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "contentLength",
    "data"
})
public class GetDocumentLink implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The contentlength schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("contentLength")
    private String contentLength = "2";
    /**
     * The data schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("data")
    private String data = "dsdfds";
    

    /**
     * The contentlength schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     * @return
     *     The contentLength
     */
    @JsonProperty("contentLength")
    public String getContentLength() {
        return contentLength;
    }

    /**
     * The contentlength schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     * @param contentLength
     *     The contentLength
     */
    @JsonProperty("contentLength")
    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * The data schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     * The data schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

  

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(contentLength).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetDocumentLink) == false) {
            return false;
        }
        GetDocumentLink rhs = (GetDocumentLink) other;
        return new EqualsBuilder().append(contentLength, rhs.contentLength).append(data, rhs.data).isEquals();
    }

}
