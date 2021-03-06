package nl.tudelft.oopp.group54.controllers;

import nl.tudelft.oopp.group54.views.MainView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public abstract class AbstractApplicationController {

  @FXML
  ToolBar utilityToolbar;

  @FXML
  Button utilityToolbarBackButton;

  public void utilityToolbarBackButtonPressed() {
    System.out.println("Back button has been pressed.");
    MainView.goBackOnceInHistory();
  }

  public abstract void performControllerSpecificSetup();

}
