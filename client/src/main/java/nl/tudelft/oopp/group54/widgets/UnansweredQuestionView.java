package nl.tudelft.oopp.group54.widgets;

import javafx.scene.control.Button;

public class UnansweredQuestionView extends QuestionView {
    private Button markAnsweredButton;
    private Button deleteButton;

    private String userId;

    /**
     * Instantiates a new Unanswered question view.
     *
     * @param s          the s
     * @param questionId the question id
     * @param userName   the user name
     * @param userIp     the user ip
     * @param voteCount  the vote count
     */
    public UnansweredQuestionView(String s, String questionId, String userName, String userIp, Integer voteCount, String userId) {
        super(s, questionId, userName, userIp, voteCount);
        super.addDropDown();
        this.addMarkAnsweredButton();
        super.horizontalGridPane.add(markAnsweredButton, 1, 0);
        this.markAnsweredButton.setVisible(false);
        this.userId = userId;
    }

    @Override
    public void toggleLecturerMode(boolean enter) {
        super.toggleLecturerMode(enter);
        if (enter) {
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
    
    private void addDeleteButton() {
        this.deleteButton = new Button("Delete");
        super.horizontalGridPane.add(deleteButton, 1, 0);
        deleteButton.setOnAction(event -> {
            super.delete();
        });
    }
    
    @Override
    public void updateQuestionView() {
        if (this.owner.getDs().getPrivilegeId().equals(3)) {
            this.dropDown.setVisible(false);
            if (Long.parseLong(userId) == super.owner.getDs().getUserId()) {
                this.addDeleteButton();
            }
        }
    }
}
