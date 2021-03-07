package nl.tudelft.oopp.group54.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.tudelft.oopp.group54.Question;
import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;

public class LectureRoomSceneController extends AbstractApplicationController {

  @FXML
  ScrollPane questionScroll;

  @FXML
  VBox questionBox;


  @Override
  public void performControllerSpecificSetup() {
	questionBox.setFillWidth(true);
    for(int i  = 0; i < 20; i++) {
      addQuestion("hello world " + i);
    }
  }

  public void addQuestion(String question){
    Question q = new Question(question);
    questionBox.getChildren().add(q);
  }
}
