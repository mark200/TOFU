package nl.tudelft.oopp.group54.widgets;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.controllers.LectureRoomSceneController;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.BanIpResponse;
import nl.tudelft.oopp.group54.models.responseentities.DeleteQuestionResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostAnswerResponse;

import nl.tudelft.oopp.group54.models.responseentities.VoteResponse;

public abstract class QuestionView extends AnchorPane {

    private VBox innerVBox;
    private GridPane outerGridPane;
    private GridPane voteGridPane;
    private GridPane verticalGridPane;
    protected GridPane horizontalGridPane;
    private MenuBar menuBar;

    private TextArea questionTextArea;
    private Text userName;

    protected MenuButton dropDown;

    private MenuItem delete;
    private MenuItem markAnswer;
    private MenuItem answerText;
    private MenuItem ban;

    private Button upvoteButton;

    private Label currentScore;

    private String text;
    private String questionId;
    private String userNameString;
    private Integer voteCount;
    private String userIp;

    private LectureRoomSceneController owner;

    /**
     * Instantiates a new Question view.
     *
     * @param text       the text
     * @param questionId the question id
     * @param userName   the user name
     * @param userIp     the user ip
     * @param voteCount  the vote count
     */
    public QuestionView(String text, String questionId, String userName, String userIp, Integer voteCount) {
        this.innerVBox = new VBox();

        this.text = text;
        this.userNameString = userName;
        this.questionId = questionId;
        this.voteCount = voteCount;
        this.userIp = userIp;

        this.menuBar = new MenuBar();

        addOuterGridPane();

        addVoteGridPane();

        addVerticalGridPane();

        addHorizontalGridPane();

        this.getChildren().addAll(this.outerGridPane, this.menuBar);

        this.owner = null;

    }

    public String getQuestionId() {
        return questionId;
    }

    private void addOuterGridPane() {
        this.outerGridPane = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(30);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setFillWidth(true);
        col2.setHgrow(Priority.ALWAYS);

        this.outerGridPane.getColumnConstraints().addAll(col1, col2);

    }


    private void addVoteGridPane() {
        this.voteGridPane = new GridPane();

        this.upvoteButton = new Button("^");
        this.currentScore = new Label(this.voteCount.toString());

        this.voteGridPane.add(this.upvoteButton, 0, 0);
        this.voteGridPane.add(this.currentScore, 0, 1);

        this.outerGridPane.add(this.voteGridPane, 0, 0);
    }

    private void addVerticalGridPane() {
        this.verticalGridPane = new GridPane();

        this.questionTextArea = new TextArea(text);

        this.questionTextArea.setWrapText(true);
        this.questionTextArea.setEditable(false);
        this.questionTextArea.setPrefRowCount(3);

        GridPane.setHgrow(this.questionTextArea, Priority.ALWAYS);

        this.questionTextArea.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        this.verticalGridPane.add(this.questionTextArea, 0, 1);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(27.5);
        this.verticalGridPane.getRowConstraints().add(row1);

        this.outerGridPane.add(this.verticalGridPane, 1, 0);
    }

    private void addHorizontalGridPane() {
        this.horizontalGridPane = new GridPane();

        this.userName = new Text(userNameString);


        this.horizontalGridPane.add(this.userName, 0, 0);

        this.horizontalGridPane.setStyle(
                "-fx-border-color: aqua;" + "-fx-border-radius: 3px"
        );

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        column2.setFillWidth(true);
        column2.setPercentWidth(50);

        this.horizontalGridPane.getColumnConstraints().addAll(column1, column2);

        this.verticalGridPane.add(this.horizontalGridPane, 0, 0);

    }

    protected void addDropDown() {
        this.dropDown = new MenuButton("Options");
        this.delete = new MenuItem("Delete");
        this.markAnswer = new MenuItem("Mark answered");
        this.answerText = new MenuItem("Answer with text");
        this.ban = new MenuItem("Ban author");

        dropDown.getItems().addAll(delete, markAnswer, answerText, ban);

        attachEventHandlers();

        this.horizontalGridPane.add(dropDown, 1, 0);
    }

    private void attachEventHandlers() {
        upvoteButton.setOnAction(event -> {
            vote();
        });

        delete.setOnAction(event -> {
            delete();
        });

        markAnswer.setOnAction(event -> {
            markAnswered();
        });

        answerText.setOnAction(event -> {
            answerWithText();
        });

        ban.setOnAction(event -> {
            banAuthor();
        });
    }

    public void setOwner(LectureRoomSceneController owner) {
        this.owner = owner;
    }

    public LectureRoomSceneController getOwner() {
        return owner;
    }


    private void childConfiguration() {
        setBottomAnchor(outerGridPane, 0.0);
        setTopAnchor(outerGridPane, 0.0);
        setLeftAnchor(outerGridPane, 0.0);
        setRightAnchor(outerGridPane, 0.0);
        setLeftAnchor(menuBar, 0.0);
        setRightAnchor(menuBar, 0.0);
    }

    private void vote() {
        VoteResponse response = null;
        try {
            response = ServerCommunication.voteOnQuestion(Integer.valueOf(questionId));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!response.isSuccess()) {
            System.out.println(response.getMessage());
        }

        if (response != null) {
            owner.displayStatusMessage(response.getMessage());
        }

        owner.refreshButtonClickedAfter();
    }

    private void delete() {
        DeleteQuestionResponse response = null;
        System.out.println("requesting");
        try {
            response = ServerCommunication.deleteQuestion(this.questionId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!response.getSuccess()) {
            System.out.println(response.getMessage());
        }
        if (response != null) {
            owner.displayStatusMessage(response.getMessage());
        }

        owner.refreshButtonClickedAfter();

    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    protected void markAnswered() {
        PostAnswerResponse response = null;

        try {
            response = ServerCommunication.postAnswer(this.questionId, "");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!response.getSuccess()) {
            System.out.println(response.getMessage());
        }

        if (response != null) {
            owner.displayStatusMessage(response.getMessage());
        }

        owner.refreshButtonClickedAfter();
    }

    private void answerWithText() {
        System.out.println("answer question " + questionId + " with text");
    }


    /**
     * Update the Options dropdown for Lecturer.
     */
    private void updateLecturer() {

    }

    /**
     * Update the Options dropdown for student.
     */
    private void updateStudent() {
        markAnswer.setVisible(false);
        answerText.setVisible(false);
        ban.setVisible(false);
    }

    /**
     * Update the Options dropdown for Moderator.
     */
    private void updateModerator() {

    }

    /**
     * Update question view.
     */
    public void updateQuestionView() {
        if (this.owner.getDs().getPrivilegeId().equals(1)) {
            updateLecturer();
        }

        if (this.owner.getDs().getPrivilegeId().equals(2)) {
            updateModerator();
        }

        if (this.owner.getDs().getPrivilegeId().equals(3)) {
            updateStudent();
        }
    }

    private void banAuthor() {
        BanIpResponse response = null;

        try {
            response = ServerCommunication.banIp(this.questionId, this.userIp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getSuccess()) {
            owner.displayStatusMessage("Users with this question's author's IP "
                    + "have been banned from posting anymore questions.");
        }

        owner.refreshButtonClickedAfter();

    }

    public void toggleLecturerMode(boolean b) {
        //implemented in child class
    }

}

