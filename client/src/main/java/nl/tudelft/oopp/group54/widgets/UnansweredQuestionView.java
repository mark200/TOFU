package nl.tudelft.oopp.group54.widgets;

import javafx.scene.control.Button;

public class UnansweredQuestionView extends QuestionView {
    private Button markAnsweredButton;


    /**
     * Instantiates a new Unanswered question view.
     *
     * @param s          the s
     * @param questionId the question id
     * @param userName   the user name
     * @param userIp     the user ip
     * @param voteCount  the vote count
     */
    public UnansweredQuestionView(String s, String questionId, String userName, String userIp, Integer voteCount) {
        super(s, questionId, userName, userIp, voteCount);
        super.addDropDown();
        this.addMarkAnsweredButton();
        super.horizontalGridPane.add(markAnsweredButton, 1, 0);
        this.markAnsweredButton.setVisible(false);
    }

    @Override
    public void toggleLecturerMode(boolean b) {
        if (b) {
            this.dropDown.setVisible(false);
            this.markAnsweredButton.setVisible(true);
        } else {
            this.markAnsweredButton.setVisible(false);
            this.dropDown.setVisible(true);
        }
    }

    private void addMarkAnsweredButton() {
        this.markAnsweredButton = new Button("Mark Answered");
        markAnsweredButton.setOnAction(event -> {
            super.markAnswered();
        });
    }
}
