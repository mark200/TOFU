package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;

public class PostQuestionResponse implements Serializable{
	private boolean success;
	private String message;
	private Integer questionID;
	
	public PostQuestionResponse() {
		this.success = false;
		this.message = "";
		this.questionID = 0;
	}
	
	public PostQuestionResponse(boolean success, String message, Integer questionID) {
		this.success = success;
		this.message = message;
		this.questionID = questionID;
	}

	public Integer getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
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
