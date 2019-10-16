package com.wba.leap.card.service;

import java.io.IOException;

import com.wba.leap.card.beans.CardRequest;
import com.wba.leap.card.beans.CardResponse;
import com.wba.leap.card.exception.BusinessException;
import com.wba.leap.card.exception.InternalException;

public interface ICardService {
	public CardResponse addCard(CardRequest cardRequest) throws IOException, BusinessException, InternalException;
}
