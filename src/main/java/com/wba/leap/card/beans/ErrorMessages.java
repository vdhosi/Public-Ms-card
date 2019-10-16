package com.wba.leap.card.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"errors",
})
public class ErrorMessages {
	@JsonProperty("errors")
	private List<ErrorMessage> errors;
	
	public List<ErrorMessage> getErrors(){
		return errors; 
	}
	
	public void setError(ErrorMessage error){
		if(this.errors==null) {
			this.errors=new ArrayList<ErrorMessage>();
		}
		this.errors.add(error);
	}

}
