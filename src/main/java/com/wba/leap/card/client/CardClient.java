package com.wba.leap.card.client;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wba.leap.card.beans.ErrorMessage;
import com.wba.leap.card.beans.ErrorMessages;
import com.wba.leap.card.config.CardConfig;
import com.wba.leap.card.exception.BusinessException;
import com.wba.leap.card.exception.InternalException;

@Service
public class CardClient {
	Logger logger = LogManager.getLogger(CardClient.class);

	@Autowired
	CardConfig config;
	
	@Autowired
	ObjectMapper jsonMapperObj;
	
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();

	public ResponseEntity<String> get(String url,Map<String, String> parameter)
			throws BusinessException, InternalException {
		setHeader();
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<String> result = null;
		ErrorMessages errorMessages = null;
		try {
			result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, parameter);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			try {
				errorMessages = jsonMapperObj.readValue(e.getResponseBodyAsString(), ErrorMessages.class);
				ErrorMessage error = errorMessages.getErrors().get(0);
				String errorType = error.getErrorType();
				if (errorType.equals("B")) {
					throw new BusinessException(
							error.getErrorId() + ":" + error.getErrorMessage() + ":" + error.getErrorSource());
				} else {
					throw new InternalException(
							error.getErrorId() + ":" + error.getErrorMessage() + ":" + error.getErrorSource());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new InternalException("979:getRxDetails Response Transformation Error:LEAP API");
			}

		}
		return result;

	}

	public ResponseEntity<String> post(String url, Object requestObject) throws BusinessException, InternalException {
		setHeader();
		HttpEntity<Object> entityObject = new HttpEntity<>(requestObject, headers);
		ResponseEntity<String> result = null;
		ErrorMessages errorMessages = null;
		try {
			result = restTemplate.exchange(url, HttpMethod.POST, entityObject, String.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			try {
				errorMessages = jsonMapperObj.readValue(e.getResponseBodyAsString(), ErrorMessages.class);
				ErrorMessage error = errorMessages.getErrors().get(0);
				String errorType = error.getErrorType();
				if (errorType.equals("B")) {
					throw new BusinessException(
							error.getErrorId() + ":" + error.getErrorMessage() + ":" + error.getErrorSource());
				} else {
					throw new InternalException(
							error.getErrorId() + ":" + error.getErrorMessage() + ":" + error.getErrorSource());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new InternalException("979:addCardDetails Response Transformation Error:LEAP API");
			}

		}
		return result;
	}

	public HttpHeaders setHeader() {
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("channel", "mobile");
		headers.set("Ocp-Apim-Subscription-Key", config.getSubscriptionKey());
		return headers;
	}

}