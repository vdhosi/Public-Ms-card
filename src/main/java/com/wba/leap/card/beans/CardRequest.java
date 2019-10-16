package com.wba.leap.card.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
@Component
public class CardRequest {
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String customerId;
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cardnbr;
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String expMonth;
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String expYear;
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;
	@NotEmpty
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	@NotNull
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long zip;
	@NotNull
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean addCard;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCardnbr() {
		return cardnbr;
	}
	public void setCardnbr(String cardnbr) {
		this.cardnbr = cardnbr;
	}
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getZip() {
		return zip;
	}
	public void setZip(Long zip) {
		this.zip = zip;
	}
	public Boolean getAddCard() {
		return addCard;
	}
	public void setAddCard(Boolean addCard) {
		this.addCard = addCard;
	}	

}
