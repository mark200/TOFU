package nl.tudelft.oopp.group54.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;

public class LectureRoomSceneController extends AbstractApplicationController {

  @FXML
  ScrollPane questionScroll;

  @FXML
  VBox questionBox;


  @Override
  public void performControllerSpecificSetup() {
    for(int i =0; i <100;i++) {
      Text text = new Text();
      text.setText("Hello World!");
      questionBox.getChildren().add(text);
    }
  }

  public void addQuestion(String question){
    Text text = new Text();
    text.setText(question);
    questionBox.getChildren().add(text);
  }
}
