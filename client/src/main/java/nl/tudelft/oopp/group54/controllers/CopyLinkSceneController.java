package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyLinkSceneController extends AbstractApplicationController {

  @FXML
  Button copyLecturerLinkButton;
  Boolean copyLecturerLinkClaimed = false;

  @FXML
  Button copyModeratorLinkButton;
  Boolean copyModeratorLinkClaimed = false;

  @FXML
  Button copyStudentLinkButton;
  Boolean copyStudentLinkClaimed = false;

  @FXML
  Button goToLectureButton;

  Datastore ds = Datastore.getInstance();
  Toolkit toolkit = Toolkit.getDefaultToolkit();
  Clipboard clipboard = toolkit.getSystemClipboard();


  CreateLectureResponse createLectureResponse;

  @Override
  public void performControllerSpecificSetup() {
    System.out.println("Copy link scene config");
    this.displayStatusMessage("Successfully created a lecture room.");
    createLectureResponse = this.ds.getCreateLectureResponse();
  }

  public void goToLectureButtonClicked() {
    if(this.copyLecturerLinkClaimed
            && this.copyStudentLinkClaimed
            && this.copyModeratorLinkClaimed) {
      MainView.changeScene(ApplicationScene.LECTUREROOM, true);
    }

    this.displayStatusMessage("You should claim all of the links before proceeding!");
  }

  public void copyLecturerLinkButtonClicked() {
    String lecturerLink = "/j/" + this.createLectureResponse.getLectureID() + "/" + String.valueOf(this.createLectureResponse.getLecturerID());
    StringSelection strSel = new StringSelection(lecturerLink);
    clipboard.setContents(strSel, null);
    this.copyLecturerLinkClaimed = true;
    this.copyLecturerLinkButton.setOpacity(0.5);
  }

  public void copyStudentLinkButtonClicked() {
    String lecturerLink = "/j/" + this.createLectureResponse.getLectureID() + "/" + String.valueOf(this.createLectureResponse.getStudentID());
    StringSelection strSel = new StringSelection(lecturerLink);
    clipboard.setContents(strSel, null);
    this.copyStudentLinkClaimed = true;
    this.copyStudentLinkButton.setOpacity(0.5);

  }

  public void copyModeratorLinkButtonClicked() {
    String lecturerLink = "/j/" + this.createLectureResponse.getLectureID() + "/" + String.valueOf(this.createLectureResponse.getModeratorID());
    StringSelection strSel = new StringSelection(lecturerLink);
    clipboard.setContents(strSel, null);
    this.copyModeratorLinkClaimed = true;
    this.copyModeratorLinkButton.setOpacity(0.5);
  }
}
