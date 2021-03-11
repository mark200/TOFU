package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostQuestionResponse implements Serializable{
	private boolean success;
	private String message;
	
	public PostQuestionResponse() {
		this.success = false;
		this.message = "";
	}
	
	public PostQuestionResponse(boolean success, String message) {
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

	@Override
	public String toString() {
		return "PostQuestionResponse [success=" + success + ", message=" + message + "]";
	}
	
	
}
