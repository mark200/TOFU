package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostAnswerResponse implements Serializable{
	boolean success;
	String message;
	
	public PostAnswerResponse() {
		
	}
	
	public PostAnswerResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
