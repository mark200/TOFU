package nl.tudelft.oopp.group54.widgets;

public class UnansweredQuestionView extends QuestionView {

	public UnansweredQuestionView(String s, String questionId, String userName, String userIp, Integer voteCount) {
		super(s, questionId, userName, userIp, voteCount);
		super.addDropDown();
	}

}
