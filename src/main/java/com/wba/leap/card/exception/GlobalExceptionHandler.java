package com.wba.leap.card.exception;

//import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.wba.leap.card.beans.ErrorMessage;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	Logger logger =
			LogManager.getLogger(GlobalExceptionHandler.class);
	
	/* @ExceptionHandler(value=Exception.class)
	public void handleException(Exception exception){
		exception.printStackTrace();

	} */
	
	@ExceptionHandler(value=MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleException(MissingServletRequestParameterException exception){
		exception.printStackTrace();
		logger.error(exception.getMessage());
		return new ResponseEntity<>(prepareErrorMessages(exception), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleException(HttpMediaTypeNotSupportedException exception) {
		//exception.printStackTrace();
		logger.error(exception.getMessage());
		return new ResponseEntity<>(prepareErrorMessages(exception), HttpStatus.UNSUPPORTED_MEDIA_TYPE);	
	}
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException exception) {
		//exception.printStackTrace();
		logger.error(exception.getMessage());
		return new ResponseEntity<>(prepareErrorMessages(exception), HttpStatus.METHOD_NOT_ALLOWED);	
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
		//exception.printStackTrace();
		logger.error("Invalid Method Argument : ",exception);
		return new ResponseEntity<>(prepareErrorMessages(exception),  HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(value = InternalException.class)
	public ResponseEntity<Object> handleException(InternalException exception) {
		//exception.printStackTrace();
		logger.error("system exception : ",exception);
		return new ResponseEntity<>(prepareErrorMessages(exception), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<Object> handleException(BusinessException exception) {
		//exception.printStackTrace();
		logger.error("business exception : ",exception);
		/* HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ArrayList<MediaType> acceptList=new ArrayList<MediaType>();
		acceptList.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptList);
		return new ResponseEntity<>(prepareErrorMessages(exception), headers, HttpStatus.BAD_REQUEST); */
		ErrorMessage errorMessage=prepareErrorMessages(exception);
		if(errorMessage.getErrorType().equals("B"))
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ErrorMessage prepareErrorMessages(MissingServletRequestParameterException exception) {
		//ErrorMessages errorMessages=new ErrorMessages();
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setErrorId("205");
		errorMessage.setErrorMessage(exception.getMessage());
		errorMessage.setErrorSource("LEAP API");
		errorMessage.setErrorType("B");
		//errorMessages.setError(errorMessage);
		//return errorMessages;
		return errorMessage;
	}
	
	private ErrorMessage prepareErrorMessages(HttpMediaTypeNotSupportedException exception) {
		//ErrorMessages errorMessages=new ErrorMessages();
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setErrorId("204");
		errorMessage.setErrorMessage(exception.getMessage());
		errorMessage.setErrorSource("LEAP API");
		errorMessage.setErrorType("B");
		//errorMessages.setError(errorMessage);
		//return errorMessages;
		return errorMessage;
	}
	
	private ErrorMessage prepareErrorMessages(HttpRequestMethodNotSupportedException exception) {
		//ErrorMessages errorMessages=new ErrorMessages();
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setErrorId("203");
		errorMessage.setErrorMessage(exception.getMessage());
		errorMessage.setErrorSource("LEAP API");
		errorMessage.setErrorType("B");
		//errorMessages.setError(errorMessage);
		//return errorMessages;
		return errorMessage;
	}
	
	private ErrorMessage prepareErrorMessages(MethodArgumentNotValidException exception) {
		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
		//ErrorMessages errorMessages=null;
		ErrorMessage errorMessage=null;
		if(errors!=null&&(!errors.isEmpty())){
			//errorMessages=new ErrorMessages();
			//for(ObjectError errorObject:errors) {
				errorMessage=new ErrorMessage();
				String errorValidation = errors.get(0).getDefaultMessage();
				String[] errorKeyValidation = errorValidation.split(":");
				if(errorKeyValidation.length==3) {
					errorMessage.setErrorId(errorKeyValidation[0]);
					errorMessage.setErrorMessage(errorKeyValidation[1]);
					errorMessage.setErrorSource(errorKeyValidation[2]);
					errorMessage.setErrorType("B");
				}else {
					errorMessage.setErrorId("999");
					errorMessage.setErrorMessage("Card Error : "+errorValidation);
					errorMessage.setErrorSource("LEAP API");
					errorMessage.setErrorType("B");

				}
				//errorMessages.setError(errorMessage);
				
			//}
		}
		return errorMessage;
	}
	
	private ErrorMessage prepareErrorMessages(InternalException exception) {
		//ErrorMessages errorMessages=new ErrorMessages();
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setErrorId(exception.getErrorId());
		errorMessage.setErrorMessage(exception.getErrorMessage());
		errorMessage.setErrorType(exception.getErrorType());
		errorMessage.setErrorSource("LEAP API");
		//errorMessages.setError(errorMessage);
		//return errorMessages;
		return errorMessage;
	}
	
	private ErrorMessage prepareErrorMessages(BusinessException exception) {
		//ErrorMessages errorMessages=new ErrorMessages();
		ErrorMessage errorMessage=new ErrorMessage();
		String loginErrorMessage=null;
		String loginErrorType=null;
		String loginErrorSource=null;
		/* if(exception.getErrorSource().equals("CUSTOMER API")) {
			String errorId=exception.getErrorId();
			if(errorId.equals("001")) {
				loginErrorMessage="Invalid Credentials";
			}else if(errorId.equals("002")) {
				loginErrorMessage="Invalid Credentials";
			}else if(errorId.equals("101")) {
				loginErrorMessage="Invalid Credentials";
			}else if(errorId.equals("102")) {
				loginErrorMessage="Invalid Credentials";
			}else if(errorId.equals("103")) {
				loginErrorMessage="Invalid Credentials";
			}else if(errorId.equals("201")) {
				loginErrorMessage="Internal Server Error";
				loginErrorType="S";
				loginErrorSource="LEAP API";
			}else if(errorId.equals("202")) {
				loginErrorMessage="Internal Server Error";
				loginErrorType="S";
				loginErrorSource="LEAP API";
			}
		} */
		
		errorMessage.setErrorId(exception.getErrorId());
		if(loginErrorMessage!=null) {
			errorMessage.setErrorMessage(loginErrorMessage);
		}else {
			errorMessage.setErrorMessage(exception.getErrorMessage());
		}
		if(loginErrorType!=null) {
			errorMessage.setErrorType(loginErrorType);
		}else {
			errorMessage.setErrorType(exception.getErrorType());
		}
		if(loginErrorSource!=null) {
			errorMessage.setErrorSource(loginErrorSource);
		}else {
			errorMessage.setErrorSource(exception.getErrorSource());
		}
		
		//errorMessages.setError(errorMessage);
		//return errorMessages;
		return errorMessage;
	}
}
