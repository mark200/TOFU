package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;
import nl.tudelft.oopp.group54.controllers.util.TextFormatterFactory;
import nl.tudelft.oopp.group54.controllers.util.TextFormatterType;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class JoinLectureSceneController extends AbstractApplicationController {

  @FXML
  Button enterLectureButton;

  @FXML
  TextField joinIdTextField;

  @FXML
  TextField enterNameTextField;


  @Override
  public void performControllerSpecificSetup() {
    System.out.println("Join controller config");
    joinIdTextField.setTextFormatter(
            TextFormatterFactory.createFormatter(TextFormatterType.JOIN_ID)
    );
    enterNameTextField.setTextFormatter(
            TextFormatterFactory.createFormatter(TextFormatterType.NAME)
    );
  }

  public void enterLectureButtonClicked() {
    MainView.changeScene(ApplicationScene.LECTUREROOM, true);
  }
}
