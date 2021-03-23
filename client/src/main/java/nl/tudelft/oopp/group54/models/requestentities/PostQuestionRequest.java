package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class PostQuestionRequest implements Serializable {
	String questionText;
	String userId;
	String userIp;
	
	public PostQuestionRequest(String questionText, String userId, String userIp) {
		this.questionText = questionText;
		this.userId = userId;
		this.userIp = userIp;
	}

	public String getUserIp() { return userIp; }

	public void setUserIp(String userIp) { this.userIp = userIp; }

	public String getQuestionText() {
		return questionText;
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PostQuestionRequest{" +
				"questionText='" + questionText + '\'' +
				", userId='" + userId + '\'' +
				", userIp='" + userIp + '\'' +
				'}';
	}
}
