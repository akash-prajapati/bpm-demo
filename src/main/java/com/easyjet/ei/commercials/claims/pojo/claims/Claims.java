package com.easyjet.ei.commercials.claims.pojo.claims;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement

public class Claims implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String paymentMethodReference;

    private String arrivalAirportCode;

    private String submittedCurrency;

    private String currencyCode;

    private String status;

    private String paymentId;

    private String claimReference;

    private String flightNumber;

    private String uri;
    
    private Integer paxCount;	
    
    private String language;
    
   
    public Integer getPaxCount() {
		return paxCount;
	}

	public void setPaxCount(Integer paxCount) {
		this.paxCount = paxCount;
	}

	private BigDecimal totalPayableAmount;

    private String claimType;

    private String reasonForClaim;

    private String flightDate;

    private String submittedCountry;

    private ClaimLines[] claimLines;
    
    private List<Passenger> passengers = null;

    private String kanaCaseReference;

    private String departureAirportCode;

    private String bookingReference;

    private String createDate;

    private String fraudResult;

    private String paymentMethod;

	private Claimant claimant;

    public String getPaymentMethodReference ()
    {
        return paymentMethodReference;
    }

    public void setPaymentMethodReference (String paymentMethodReference)
    {
        this.paymentMethodReference = paymentMethodReference;
    }

    public String getArrivalAirportCode ()
    {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode (String arrivalAirportCode)
    {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getSubmittedCurrency ()
    {
        return submittedCurrency;
    }

    public void setSubmittedCurrency (String submittedCurrency)
    {
        this.submittedCurrency = submittedCurrency;
    }

    public String getCurrencyCode ()
    {
        return currencyCode;
    }

    public void setCurrencyCode (String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getPaymentId ()
    {
        return paymentId;
    }

    public void setPaymentId (String paymentId)
    {
        this.paymentId = paymentId;
    }

    public String getClaimReference ()
    {
        return claimReference;
    }

    public void setClaimReference (String claimReference)
    {
        this.claimReference = claimReference;
    }

    public String getFlightNumber ()
    {
        return flightNumber;
    }

    public void setFlightNumber (String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    public BigDecimal getTotalPayableAmount ()
    {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount (BigDecimal totalPayableAmount)
    {
        this.totalPayableAmount = totalPayableAmount;
    }

    public String getClaimType ()
    {
        return claimType;
    }

    public void setClaimType (String claimType)
    {
        this.claimType = claimType;
    }

    public String getReasonForClaim ()
    {
        return reasonForClaim;
    }

    public void setReasonForClaim (String reasonForClaim)
    {
        this.reasonForClaim = reasonForClaim;
    }

    public String getFlightDate ()
    {
        return flightDate;
    }

    public void setFlightDate (String flightDate)
    {
        this.flightDate = flightDate;
    }

    public String getSubmittedCountry ()
    {
        return submittedCountry;
    }

    public void setSubmittedCountry (String submittedCountry)
    {
        this.submittedCountry = submittedCountry;
    }

    public ClaimLines[] getClaimLines ()
    {
        return claimLines;
    }

    public void setClaimLines (ClaimLines[] claimLines)
    {
        this.claimLines = claimLines;
    }

    public String getKanaCaseReference ()
    {
        return kanaCaseReference;
    }

    public void setKanaCaseReference (String kanaCaseReference)
    {
        this.kanaCaseReference = kanaCaseReference;
    }

    public String getDepartureAirportCode ()
    {
        return departureAirportCode;
    }

    public void setDepartureAirportCode (String departureAirportCode)
    {
        this.departureAirportCode = departureAirportCode;
    }

    public String getBookingReference ()
    {
        return bookingReference;
    }

    public void setBookingReference (String bookingReference)
    {
        this.bookingReference = bookingReference;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }

    public String getFraudResult ()
    {
        return fraudResult;
    }

    public void setFraudResult (String fraudResult)
    {
        this.fraudResult = fraudResult;
    }

    public String getPaymentMethod ()
    {
        return paymentMethod;
    }

    public void setPaymentMethod (String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public Claimant getClaimant ()
    {
        return claimant;
    }

    public void setClaimant (Claimant claimant)
    {
        this.claimant = claimant;
    }

    @Override
    public String toString()
    {
    	
        return "ClassPojo [paymentMethodReference = "+paymentMethodReference+", arrivalAirportCode = "+arrivalAirportCode+", submittedCurrency = "+submittedCurrency+", currencyCode = "+currencyCode+", status = "+status+", paymentId = "+paymentId+", claimReference = "+claimReference+", flightNumber = "+flightNumber+", uri = "+uri+", totalPayableAmount = "+totalPayableAmount+", claimType = "+claimType+", reasonForClaim = "+reasonForClaim+", flightDate = "+flightDate+", submittedCountry = "+submittedCountry+", claimLines = "+claimLines+", kanaCaseReference = "+kanaCaseReference+", departureAirportCode = "+departureAirportCode+", bookingReference = "+bookingReference+", createDate = "+createDate+", fraudResult = "+fraudResult+", paymentMethod = "+paymentMethod+", claimant = "+claimant+"]";
    }

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
    
    
}
		