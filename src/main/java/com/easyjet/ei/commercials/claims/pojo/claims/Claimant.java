package com.easyjet.ei.commercials.claims.pojo.claims;
//@org.kie.api.remote.Remotable
public class Claimant implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isLeadBooker;

    private String lastName;

    private Address address;

    private String travelAgent;

    private String emailAddress;

    private String firstName;

    private String telephone;

    public boolean getIsLeadBooker ()
    {
        return isLeadBooker;
    }

    public void setIsLeadBooker (boolean isLeadBooker)
    {
        this.isLeadBooker = isLeadBooker;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public Address getAddress ()
    {
        return address;
    }

    public void setAddress (Address address)
    {
        this.address = address;
    }

    public String getTravelAgent ()
    {
        return travelAgent;
    }

    public void setTravelAgent (String travelAgent)
    {
        this.travelAgent = travelAgent;
    }

    public String getEmailAddress ()
    {
        return emailAddress;
    }

    public void setEmailAddress (String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getTelephone ()
    {
        return telephone;
    }

    public void setTelephone (String telephone)
    {
        this.telephone = telephone;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isLeadBooker = "+isLeadBooker+", lastName = "+lastName+", address = "+address+", travelAgent = "+travelAgent+", emailAddress = "+emailAddress+", firstName = "+firstName+", telephone = "+telephone+"]";
    }
}