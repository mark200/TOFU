package nl.tudelft.oopp.group54.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.responseentities.GetAllQuestionsResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.PostQuestionResponse;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;
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

  Datastore ds = Datastore.getInstance();


  @Override
  public void performControllerSpecificSetup() {
    answeredQuestionView.setItems(ds.getCurrentAnsweredQuestionViews());
    unansweredQuestionView.setItems(ds.getCurrentUnansweredQuestionViews());

    ds.addUnansweredQuestion("Lorem Ipsum is simply dummy text of the printing and " +
            "typesetting industry. Lorem Ipsum has been the industry's standard " +
            "dummy text ever since the 1500s, when an unknown printer took a " +
            "galley of type and scrambled it to make a type specimen book. " +
            "It has survived not only five centuries, but also the leap into " +
            "electronic typesetting, remaining essentially unchanged. It was " +
            "popularised in the 1960s with the release of Letraset sheets " +
            "containing Lorem Ipsum passages, and more recently with desktop " +
            "publishing software like Aldus PageMaker including versions of " +
            "Lorem Ipsum.");

    for(int i  = 0; i < 20; i++) {
      ds.addUnansweredQuestion("hello world " + i);
      ds.addAnsweredQuestion("hello world " + i);
    }
    
    questionField.setOnKeyPressed(event -> {
    	keyPressed(event);
    });
  }
  
  public void askButtonClicked() {
	  postQuestion();
  }
  
  public void keyPressed(KeyEvent event) {
	  if(event.getCode() == KeyCode.ENTER) {
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

          //should anything(like storing the response) happen here?
          this.ds.addUnansweredQuestion(questionText);
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

      if (response.getSuccess()) {
    	  this.displayStatusMessage("Refreshed succesfully.");
      }
  }
}
