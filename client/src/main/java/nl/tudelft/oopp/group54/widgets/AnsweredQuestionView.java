package nl.tudelft.oopp.group54.widgets;

public class AnsweredQuestionView extends QuestionView {

    /**
     * Constructor for answeredQuestionView.
     * 
     * @param s the question
     * @param questionId the question id
     * @param userName the user name
     * @param userIp the user ip
     * @param voteCount the vote count
     * @param answer the answer
     */
    public AnsweredQuestionView(String s, String questionId, String userName, String userIp, Integer voteCount, String answer) {
        super(s, questionId, userName, userIp, voteCount);
        if (!answer.equals("")) {
            super.toggleAnswerTextArea(true);
            super.answerTextArea.setEditable(false);
            super.answerTextArea.setText(answer);
        }
        //this is the only way I could get the alignment of the username to be consistent
        super.addDropDown();
        super.dropDown.setVisible(false);
    }
}