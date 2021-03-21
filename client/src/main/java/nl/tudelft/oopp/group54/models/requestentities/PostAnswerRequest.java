package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class PostAnswerRequest implements Serializable{
	String userId;
	String answerText;
	
	public PostAnswerRequest() {
		
	}
	
	public PostAnswerRequest(String userId, String answerText) {
		this.userId = userId;
		this.answerText = answerText;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getAnswerText() {
		return answerText;
	}
	
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
	
}
