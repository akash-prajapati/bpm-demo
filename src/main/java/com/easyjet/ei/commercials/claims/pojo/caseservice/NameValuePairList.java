
package com.easyjet.ei.commercials.claims.pojo.caseservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NameValuePairList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameValuePairList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NameValuePair" type="{http://localhost:80/CaseServiceService.wsdl}NameValuePair" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameValuePairList", propOrder = {
    "nameValuePair"
})
public class NameValuePairList implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "NameValuePair")
    protected List<NameValuePair> nameValuePair;

    /**
     * Gets the value of the nameValuePair property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameValuePair property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameValuePair().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePair }
     * 
     * 
     */
    public List<NameValuePair> getNameValuePair() {
        if (nameValuePair == null) {
            nameValuePair = new ArrayList<NameValuePair>();
        }
        return this.nameValuePair;
    }
    
    public void setNameValuePair(List<NameValuePair> nameValuePair) {
		this.nameValuePair = nameValuePair;
	}

    /**
     * Generates a String representation of the contents of this type.
     * This is an extension method, produced by the 'ts' xjc plugin
     * 
     */
/*    @Override
    public String toString() {
        return JAXBToStringBuilder.valueOf(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
*/
}
