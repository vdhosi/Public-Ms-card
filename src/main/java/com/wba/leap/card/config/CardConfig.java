package com.wba.leap.card.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
	@PropertySource("classpath:card.properties")
})
public class CardConfig{
	@Value( "${card.cardUrl}" )
	private String cardUrl;
	@Value( "${card.paymentUrl}" )
	private String paymentUrl;
	@Value( "${card.connectionTimeout}" )
	private String connectionTimeout;
	@Value( "${card.requestTimeout}" )
	private String requestTimeOut;
	@Value( "${card.httpHeader.Content-Type}" )
	private String contentType;
	@Value( "${card.httpHeader.Accept}" )
	private String accept;
	@Value( "${card.httpHeader.Method}" )
	private String method;
	@Value( "${card.httpHeader.subscriptionKey}" )
	private String subscriptionKey;
	
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	
	public String getPaymentUrl() {
		return paymentUrl;
	}
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	public String getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getConnectionTimeOut() {
		return connectionTimeout;
	}
	public void setConnectionTimeOut(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getRequestTimeOut() {
		return requestTimeOut;
	}
	public void setRequestTimeOut(String requestTimeOut) {
		this.requestTimeOut = requestTimeOut;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSubscriptionKey() {
		return subscriptionKey;
	}
	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}
	
}
