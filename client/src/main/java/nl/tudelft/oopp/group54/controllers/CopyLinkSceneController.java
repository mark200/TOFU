package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;

public class CopyLinkSceneController extends AbstractApplicationController {

  @FXML
  Button copyLecturerLinkButton;

  @FXML
  Button copyModeratorLinkButton;

  @FXML
  Button copyStudentLinkButton;

  @FXML
  Button goToLectureButton;

  @Override
  public void performControllerSpecificSetup() {
    System.out.println("Copy link scene config");
  }

  public void goToLectureButtonClicked() {
    MainView.changeScene(ApplicationScene.LECTUREROOM, true);
  }
}
