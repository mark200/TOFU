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
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.widgets.QuestionView;

import java.io.IOException;

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
        questionField.setOnKeyPressed(event -> {
            keyPressed(event);
        });
    }

    public void askButtonClicked() {
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
        try {
            response = ServerCommunication.postQuestion(questionText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (response.getSuccess()) {
            questionField.clear();
            this.refreshButtonClicked();
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

            this.questionField.setEditable(false);
            this.questionField.setDisable(true);
            this.askButton.setDisable(true);
            this.endLectureButton.setVisible(false);

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
}
