package com.wba.leap.card.service.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wba.leap.card.beans.CardRequest;
import com.wba.leap.card.beans.CardResponse;
import com.wba.leap.card.client.CardClient;
import com.wba.leap.card.config.CardConfig;
import com.wba.leap.card.exception.BusinessException;
import com.wba.leap.card.exception.InternalException;
import com.wba.leap.card.service.ICardService;


@Component
public class CardService implements ICardService {

	Logger logger = LogManager.getLogger(CardService.class);

	@Autowired
	CardClient cardClient;

	@Autowired
	CardConfig config;

	@Autowired
	ObjectMapper objMapper;
	
	@Autowired
	CardResponse cardResponse;
	

	@Override
	public CardResponse addCard(CardRequest cardRequest) throws IOException, BusinessException, InternalException {
		ResponseEntity<String> cardResponseEntity = cardClient.post(config.getCardUrl(),cardRequest);
		ResponseEntity<String> paymentResponseEntity = cardClient.post(config.getPaymentUrl(),cardRequest);
		if(cardResponseEntity.getStatusCodeValue()!=200 || paymentResponseEntity.getStatusCodeValue()!=200) {
			throw new BusinessException("101: Card couldn't be added:LEAP API");
		}
		cardResponse.setStatus(true);
		return cardResponse;
	}
	
}
