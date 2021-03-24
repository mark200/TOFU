package nl.tudelft.oopp.group54.widgets;

public class UnansweredQuestionView extends QuestionView {

    public UnansweredQuestionView(String s, String questionId, String userName, Integer voteCount) {
        super(s, questionId, userName, voteCount);
        super.addDropDown();
    }

}
