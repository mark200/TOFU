package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.Datastore;
import nl.tudelft.oopp.group54.communication.ServerCommunication;
import nl.tudelft.oopp.group54.models.responseentities.CreateLectureResponse;
import nl.tudelft.oopp.group54.models.responseentities.JoinLectureResponse;
import nl.tudelft.oopp.group54.util.TextFormatterFactory;
import nl.tudelft.oopp.group54.util.TextFormatterType;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.Long.parseLong;

public class JoinLectureSceneController extends AbstractApplicationController {

  @FXML
  Button enterLectureButton;

  @FXML
  TextField joinIdTextField;

  @FXML
  TextField enterNameTextField;

  Datastore ds = Datastore.getInstance();


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

    CharSequence joinIdTextFieldText = joinIdTextField.getCharacters();
    CharSequence enterNameTextFieldText = enterNameTextField.getCharacters();

    boolean joinIdTextFieldMissing = joinIdTextFieldText.length() <= 0;
    boolean enterNameTextFieldMissing = enterNameTextFieldText.length() <= 0;

    if (joinIdTextFieldMissing || enterNameTextFieldMissing) {

      if(joinIdTextFieldMissing) {
        this.shakeWidget(this.joinIdTextField, 2.0);
      }

      if(enterNameTextFieldMissing) {
        this.shakeWidget(this.enterNameTextField, -2.0);
      }

      this.displayStatusMessage("Please fill in the missing necessary information.");

      return;
    }
    
    String joinIdTextFieldString = joinIdTextFieldText.toString();
    
    
    Long lectureId = Long.parseLong(joinIdTextFieldString.substring(3, 9));
    Long joinId = Long.parseLong(joinIdTextFieldString.substring(10, 16));
    

    JoinLectureResponse response = null;
    String userIp = null;

    try {
      userIp = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    try {
      response = ServerCommunication.joinLecture(enterNameTextFieldText.toString(), lectureId, joinId);
    } catch (Exception e) {
      e.printStackTrace();
      this.displayStatusMessage(e.getMessage());
    }

    if(response.getSuccess()) {
      this.ds.setJoinLectureResponse(response);
      this.ds.setUserId(response.getUserID());
      this.ds.setLectureId(lectureId);
      this.ds.setUserIp(parseLong(userIp));
      MainView.changeScene(ApplicationScene.COPYLINK, true);
    }


    MainView.changeScene(ApplicationScene.LECTUREROOM, true);
  }
}
