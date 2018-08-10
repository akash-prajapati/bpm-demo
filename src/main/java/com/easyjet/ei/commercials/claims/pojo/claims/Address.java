package com.easyjet.ei.commercials.claims.pojo.claims;
//@org.kie.api.remote.Remotable
public class Address implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String county;

    private String postCode;

    private String town;

    private String addressLine2;

    private String addressLine1;

    private String country;

    public String getCounty ()
    {
        return county;
    }

    public void setCounty (String county)
    {
        this.county = county;
    }

    public String getPostCode ()
    {
        return postCode;
    }

    public void setPostCode (String postCode)
    {
        this.postCode = postCode;
    }

    public String getTown ()
    {
        return town;
    }

    public void setTown (String town)
    {
        this.town = town;
    }

    public String getAddressLine2 ()
    {
        return addressLine2;
    }

    public void setAddressLine2 (String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1 ()
    {
        return addressLine1;
    }

    public void setAddressLine1 (String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [county = "+county+", postCode = "+postCode+", town = "+town+", addressLine2 = "+addressLine2+", addressLine1 = "+addressLine1+", country = "+country+"]";
    }
}