package nl.tudelft.oopp.group54.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.widgets.QuestionView;

public class LectureRoomSceneController extends AbstractApplicationController {

//  @FXML
//  ScrollPane questionScroll;

  @FXML
  ListView<QuestionView> answeredQuestionView;

  @FXML
  ListView<QuestionView> unansweredQuestionView;

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

  }
}
