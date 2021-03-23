package nl.tudelft.oopp.group54.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.QuestionModel;
import nl.tudelft.oopp.group54.models.responseentities.EndLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.GetLectureMetadataResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;
import nl.tudelft.oopp.group54.widgets.QuestionView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.Long.parseLong;

public class LectureRoomSceneController extends AbstractApplicationController {

//  @FXML
//  ScrollPane questionScroll;


    @FXML
    ListView<QuestionView> answeredQuestionView;

    @FXML
    ListView<QuestionView> unansweredQuestionView;

    @FXML
    TextField questionField;

    @FXML
    Button askButton;

    @FXML
    Button endLectureButton;

    @FXML
    Button feedbackPanelButton;

    @FXML
    Accordion feedbackMenu;
    @FXML
    ColumnConstraints feedbackMenuContainer;
    Integer feedbackMenuContainerUnfoldedWidth = 140;

    Datastore ds = Datastore.getInstance();


    @Override
    public void performControllerSpecificSetup() {
        answeredQuestionView.setItems(ds.getCurrentAnsweredQuestionViews());
        unansweredQuestionView.setItems(ds.getCurrentUnansweredQuestionViews());
//    ds.addUnansweredQuestion("Lorem Ipsum is simply dummy text of the printing and " +
//            "typesetting industry. Lorem Ipsum has been the industry's standard " +
//            "dummy text ever since the 1500s, when an unknown printer took a " +
//            "galley of type and scrambled it to make a type specimen book. " +
//            "It has survived not only five centuries, but also the leap into " +
//            "electronic typesetting, remaining essentially unchanged. It was " +
//            "popularised in the 1960s with the release of Letraset sheets " +
//            "containing Lorem Ipsum passages, and more recently with desktop " +
//            "publishing software like Aldus PageMaker including versions of " +
//            "Lorem Ipsum.");
//
//    for(int i  = 0; i < 20; i++) {
//      ds.addUnansweredQuestion("hello world " + i);
//      ds.addAnsweredQuestion("hello world " + i);
//    }
//
        // lecturer
        if (this.ds.getPrivilegeId().equals(1)) {
            //TODO: GUI elements for the lecturer
        }

        // moderator
        if (this.ds.getPrivilegeId().equals(2)) {
            //TODO: GUI elements for the moderator
        }

        // student
        if (this.ds.getPrivilegeId().equals(3)) {
            //TODO: GUI elements for the student
            this.endLectureButton.setVisible(false);
        }

        questionField.setOnKeyPressed(event -> {
            keyPressed(event);
        });
    }

    public void askButtonClicked() {
        System.out.println("haha");
        postQuestion();
        this.refreshButtonClickedAfter();
    }

    public void endLectureButtonClicked() {
        endLecture();
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            postQuestion();
        }
    }

    private void postQuestion() {
        String questionText = questionField.getCharacters().toString();
        PostQuestionResponse response = null;
        String userIp = null;

        try {
            userIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            response = ServerCommunication.postQuestion(questionText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getSuccess()) {

            //should anything(like storing the response) happen here?
            //this.ds.addUnansweredQuestion(questionText);
            //this.ds.setUserIp(parseLong(userIp.replaceAll(",", "")));
            questionField.clear();
        }
    }


    public void refreshButtonClicked() {
        GetAllQuestionsResponse response = null;

        try {
            response = ServerCommunication.getAllQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetLectureMetadataResponse metadataResponse = null;

        try {
            metadataResponse = ServerCommunication.getLectureMetadata();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        if (response.getSuccess()) {
            this.displayStatusMessage("Refreshed succesfully.");
            this.ds.setCurrentUnansweredQuestionViews(null);
            this.ds.setCurrentAnsweredQuestionViews(null);
            for (QuestionModel question : response.getAnswered()) {
                this.ds.addAnsweredQuestion(question, this);
            }
            for (QuestionModel question : response.getUnanswered()) {
                this.ds.addUnansweredQuestion(question, this);
            }
        }
        if (metadataResponse != null) {
            if (metadataResponse.getSuccess()) {
                if (!metadataResponse.getLectureOngoing()) {
                    //TODO: stuff when lecture has ended change to main menu view
                    endLectureGUI();
                    //TODO: update status bar to ended
                    if(ds.getPrivilegeId().equals(3)){
                        MainView.changeSceneClearHistory(ApplicationScene.MAINVIEW, false, true);
                        this.displayStatusMessage("The Lecture has been ended.");
                    }



                }
            }
        }

    }

    public void endLecture() {
        EndLectureResponse response = null;

        try {
            response = ServerCommunication.endLecture();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response == null) {
            this.displayStatusMessage("Could not end the lecture!");
            return;
        }

        this.displayStatusMessage(response.getMessage());

        if (response.getSuccess()) {
            endLectureGUI();
        }
    }

    public void refreshButtonClickedAfter() {
        GetAllQuestionsResponse response = null;

        try {
            response = ServerCommunication.getAllQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getSuccess()) {
            this.ds.setCurrentUnansweredQuestionViews(null);
            this.ds.setCurrentAnsweredQuestionViews(null);
            for (QuestionModel question : response.getAnswered()) {
                this.ds.addAnsweredQuestion(question, this);
            }
            for (QuestionModel question : response.getUnanswered()) {
                this.ds.addUnansweredQuestion(question, this);
            }
        }
    }

    public void toggleFeedbackPanelVisibility() {
        boolean vis = !this.feedbackMenu.isVisible();
        this.feedbackMenu.setVisible(vis);

        if (vis) {
            this.feedbackMenuContainer.setPrefWidth(feedbackMenuContainerUnfoldedWidth);
        } else {
            this.feedbackMenuContainer.setPrefWidth(0);
        }
    }

    /**
     * sets GUI element to the state of lecture has finished
     */
    public void endLectureGUI() {
        this.questionField.setEditable(false);
        this.questionField.setDisable(true);
        this.askButton.setDisable(true);
        this.endLectureButton.setVisible(false);
    }
}
