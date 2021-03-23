package nl.tudelft.oopp.group54.controllers;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;


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
    TextField usernameField;

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

    /**
     * Go to lecture button clicked.
     */
    public void goToLectureButtonClicked() {
        if (this.copyLecturerLinkClaimed
                && this.copyStudentLinkClaimed
                && this.copyModeratorLinkClaimed) {

            if (usernameField.getText().length() != 0) {
                joinLecture();
            } else {
                this.shakeWidget(usernameField);
                this.displayStatusMessage("Please enter a username!");
            }

        } else {
            this.displayStatusMessage("You should claim all of the links before proceeding!");
        }

    }

    private void joinLecture() {
        JoinLectureResponse response = null;
        try {
            response = ServerCommunication.joinLecture(usernameField.getCharacters().toString(),
                    this.createLectureResponse.getLectureId(), this.createLectureResponse.getLecturerId());
        } catch (Exception e) {
            e.printStackTrace();
            this.displayStatusMessage(e.getMessage());
        }

        if (response.getSuccess()) {
            this.ds.setJoinLectureResponse(response);
            this.ds.setUserId(response.getUserID());
            this.ds.setLectureId(this.createLectureResponse.getLectureId());
            MainView.changeScene(ApplicationScene.LECTUREROOM, true);
        } else {
            this.displayStatusMessage(response.getMessage());
        }
    }

    /**
     * Copy lecturer link button clicked.
     */
    public void copyLecturerLinkButtonClicked() {
        String lecturerLink = "/j/" + this.createLectureResponse.getLectureId() + "/"
                + String.valueOf(this.createLectureResponse.getLecturerId());
        StringSelection strSel = new StringSelection(lecturerLink);
        clipboard.setContents(strSel, null);
        this.copyLecturerLinkClaimed = true;
        this.copyLecturerLinkButton.setOpacity(0.5);
    }

    /**
     * Copy student link button clicked.
     */
    public void copyStudentLinkButtonClicked() {
        String lecturerLink = "/j/" + this.createLectureResponse.getLectureId() + "/"
                + String.valueOf(this.createLectureResponse.getStudentId());
        StringSelection strSel = new StringSelection(lecturerLink);
        clipboard.setContents(strSel, null);
        this.copyStudentLinkClaimed = true;
        this.copyStudentLinkButton.setOpacity(0.5);

    }

    /**
     * Copy moderator link button clicked.
     */
    public void copyModeratorLinkButtonClicked() {
        String lecturerLink = "/j/" + this.createLectureResponse.getLectureId() + "/"
                + String.valueOf(this.createLectureResponse.getModeratorId());
        StringSelection strSel = new StringSelection(lecturerLink);
        clipboard.setContents(strSel, null);
        this.copyModeratorLinkClaimed = true;
        this.copyModeratorLinkButton.setOpacity(0.5);
    }
}
