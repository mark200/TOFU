package nl.tudelft.oopp.group54.models.responseentities;

import java.io.Serializable;
import java.util.List;

import nl.tudelft.oopp.group54.models.QuestionModel;

public class GetAllQuestionsResponse implements Serializable{
	private List<QuestionModel> answered;
	private List<QuestionModel> unanswered;
	private boolean success;
	private int count;
	
	public GetAllQuestionsResponse() {
		
	}
	
	public GetAllQuestionsResponse(List<QuestionModel> answered, List<QuestionModel> unanswered, boolean success,
			int count) {
		this.answered = answered;
		this.unanswered = unanswered;
		this.success = success;
		this.count = count;
	}

	public List<QuestionModel> getAnswered() {
		return answered;
	}
	
	public void setAnswered(List<QuestionModel> answered) {
		this.answered = answered;
	}
	
	public List<QuestionModel> getUnanswered() {
		return unanswered;
	}
	
	public void setUnanswered(List<QuestionModel> unanswered) {
		this.unanswered = unanswered;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	
	
	
}
