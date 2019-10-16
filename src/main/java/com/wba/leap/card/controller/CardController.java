package com.wba.leap.card.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wba.leap.card.beans.CardRequest;
import com.wba.leap.card.beans.CardResponse;
import com.wba.leap.card.exception.BusinessException;
import com.wba.leap.card.exception.InternalException;
import com.wba.leap.card.service.ICardService;

@RestController
public class CardController {

	@Autowired
	ICardService cardService;
	@Autowired
	CardResponse cardResponse;

	Logger logger = LogManager.getLogger(CardController.class);

	@PostMapping(value = "/payment/v1/card",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addCard(@RequestHeader(value = "Channel") String channel,
			@RequestHeader(value = "MessageId", required = false) String messageId,
			@RequestHeader(value = "accessToken") String accessToken,
			@Valid @RequestBody CardRequest cardRequest) throws InternalException, BusinessException, IOException {
		
		ObjectMapper jsonMapperObj = new ObjectMapper();
		String reqObject;
		try {
			reqObject = jsonMapperObj.writeValueAsString(cardRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new InternalException("301:Add Card Request Tranformation Error:LEAP API");
		}
		if(reqObject!=null) {
			cardResponse=cardService.addCard(cardRequest);
			
		}
		return new ResponseEntity<>(cardResponse,HttpStatus.OK);
	}
}
