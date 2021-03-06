package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;
import nl.tudelft.oopp.group54.controllers.util.TextFormatterFactory;
import nl.tudelft.oopp.group54.controllers.util.TextFormatterType;
import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DateTimeSceneController extends AbstractApplicationController {

  @FXML
  DatePicker datePicker;

  @FXML
  TextField timePrompt;

  @FXML
  Button confirmationButton;

  public void performControllerSpecificSetup() {
    System.out.println("DateTime controller config");

    timePrompt.setTextFormatter(
            TextFormatterFactory.createFormatter(TextFormatterType.TIME)
    );

  }

  public void confirmationButtonClicked() {
    MainView.changeScene(ApplicationScene.COPYLINK, true);
  }

}
