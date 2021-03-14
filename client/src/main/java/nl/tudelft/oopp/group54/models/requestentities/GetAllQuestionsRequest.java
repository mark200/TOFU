package nl.tudelft.oopp.group54.models.requestentities;

import java.io.Serializable;

public class GetAllQuestionsRequest implements Serializable{
	private String userId;
	
	public GetAllQuestionsRequest(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "getAllQuestionsRequest [userId=" + userId + "]";
	}
	
	
}
