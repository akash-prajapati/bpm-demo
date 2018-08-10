
package com.easyjet.ei.commercials.claims.pojo.inputmsg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "documentLink"
})*/
public class InitiateClaimProcessRequestDocumentLinks implements Serializable {


	  /**
	 * 
	 */
	
	private String documentLink = null;
	  public InitiateClaimProcessRequestDocumentLinks documentLink(String documentLink) {
	    this.documentLink = documentLink;
	    return this;
	  }
	   /**
	   * This is the link to storage where the receipts for welfare claims are stored by Webform.
	   * @return documentLink
	  **/

	  public String getDocumentLink() {
	    return documentLink;
	  }
	  public void setDocumentLink(String documentLink) {
	    this.documentLink = documentLink;
	  }
	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    InitiateClaimProcessRequestDocumentLinks initiateClaimProcessRequestDocumentLinks = (InitiateClaimProcessRequestDocumentLinks) o;
	    return Objects.equals(this.documentLink, initiateClaimProcessRequestDocumentLinks.documentLink);
	  }
	  @Override
	  public int hashCode() {
	    return Objects.hash(documentLink);
	  }
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class InitiateClaimProcessRequestDocumentLinks {\n");
	    
	    sb.append("    documentLink: ").append(toIndentedString(documentLink)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }
	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }

}
