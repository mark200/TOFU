package nl.tudelft.oopp.group54.widgets;

public class UnansweredQuestionView extends QuestionView {

	public UnansweredQuestionView(String s, String questionId, String userName) {
		super(s, questionId, userName);
		super.addDropDown();
	}

}
