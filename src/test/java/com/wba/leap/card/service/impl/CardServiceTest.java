package com.wba.leap.card.service.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wba.leap.card.beans.CardRequest;
import com.wba.leap.card.beans.CardResponse;
import com.wba.leap.card.client.CardClient;
import com.wba.leap.card.config.CardConfig;
import com.wba.leap.card.exception.BusinessException;
import com.wba.leap.card.exception.InternalException;

/**
 * Test case to test Card Service
 *  
 * @author vsrah
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

	@InjectMocks
	CardService cardServiceMock;

	@Mock
	CardClient cardClient;

	@Mock
	CardConfig config;

	@Mock
	ObjectMapper objMapper;

	@Mock
	CardResponse cardResponse;

	CardRequest cardRequest;
	
	/**
	 * Before Setup to set card and payment service url
	 * 
	 * @throws IOException
	 * @throws BusinessException
	 * @throws InternalException
	 */
	@Before
	public void setUp() throws IOException, BusinessException, InternalException {
		Mockito.when(config.getCardUrl()).thenReturn("https://harmony-apim.azurefd.net/payment/card");
		Mockito.when(config.getPaymentUrl()).thenReturn("https://harmony-apim.azurefd.net/payment/paymentdetails");
	}
	
	/**
	 * Test case to test card and payment details service API with Response: 200
	 * 
	 * @throws IOException
	 * @throws BusinessException
	 * @throws InternalException
	 */
	@Test
	public void addCardTest() throws IOException, BusinessException, InternalException {
		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/card"),
				Mockito.any(CardRequest.class))).thenReturn(new ResponseEntity<String>("card", HttpStatus.OK));

		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/paymentdetails"),
				Mockito.any(CardRequest.class))).thenReturn(new ResponseEntity<String>("Payment", HttpStatus.OK));

		CardResponse cardResponse = cardServiceMock.addCard(getCardRequest());
		Assert.assertNotNull(cardResponse);
	}
	
	/**
	 * Test case to test card API with Bad request and Payment detail API with 200
	 * 
	 * @throws IOException
	 * @throws BusinessException
	 * @throws InternalException
	 */
	@Test(expected = BusinessException.class)
	public void addCardPaymentExceptionTest() throws IOException, BusinessException, InternalException {
		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/card"), Mockito.any(CardRequest.class)))
				.thenReturn(new ResponseEntity<String>("card", HttpStatus.BAD_REQUEST));

		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/paymentdetails"),
				Mockito.any(CardRequest.class))).thenReturn(new ResponseEntity<String>("Payment", HttpStatus.OK));

		cardServiceMock.addCard(getCardRequest());
	}
	
	/**
	 * Test case to test card API with 200 and Payment detail API with Bad Request
	 * 
	 * @throws IOException
	 * @throws BusinessException
	 * @throws InternalException
	 */
	@Test(expected = BusinessException.class)
	public void addCardPaymentDetailsExceptionTest() throws IOException, BusinessException, InternalException {
		Mockito.when(cardClient.post(Mockito.any(String.class), Mockito.any(CardRequest.class)))
				.thenReturn(new ResponseEntity<String>("card", HttpStatus.OK));

		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/paymentdetails"),
				Mockito.any(CardRequest.class))).thenReturn(new ResponseEntity<String>("Payment", HttpStatus.BAD_REQUEST));

		cardServiceMock.addCard(getCardRequest());
	}
	
	/**
	 * Test case to test both card and payment details API with Bad Response
	 * 
	 * @throws IOException
	 * @throws BusinessException
	 * @throws InternalException
	 */
	@Test(expected = BusinessException.class)
	public void addCardAndPaymentDetailsExceptionTest() throws IOException, BusinessException, InternalException {
		Mockito.when(cardClient.post(Mockito.any(String.class), Mockito.any(CardRequest.class)))
				.thenReturn(new ResponseEntity<String>("card", HttpStatus.BAD_REQUEST));

		Mockito.when(cardClient.post(Mockito.eq("https://harmony-apim.azurefd.net/payment/paymentdetails"),
				Mockito.any(CardRequest.class))).thenReturn(new ResponseEntity<String>("Payment", HttpStatus.BAD_REQUEST));

		cardServiceMock.addCard(getCardRequest());
	}
	
	/**
	 * Creating Request object for test cases
	 * 
	 * @return
	 */
	public CardRequest getCardRequest() {
		cardRequest = new CardRequest();
		cardRequest.setAddCard(true);
		cardRequest.setCardnbr("");
		cardRequest.setCustomerId("");
		cardRequest.setExpMonth("");
		cardRequest.setExpYear("");
		cardRequest.setFirstName("");
		cardRequest.setLastName("");
		cardRequest.setZip(123L);
		
		return cardRequest;
	}

}
