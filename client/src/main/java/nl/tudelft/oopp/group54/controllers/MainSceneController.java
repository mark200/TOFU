package nl.tudelft.oopp.group54.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import nl.tudelft.oopp.group54.views.ApplicationScene;
import nl.tudelft.oopp.group54.views.MainView;

public class MainSceneController extends AbstractApplicationController {

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    @FXML
    private AnchorPane anchorPane;

    public void createButtonClicked() {
        MainView.changeScene(ApplicationScene.DATETIME, true);
    }
    
    public void joinButtonClicked() {
        MainView.changeScene(ApplicationScene.JOINLECTURE, true);
    }

    @Override
    public void performControllerSpecificSetup() {
        System.out.println("Main controller config");
    }
}
