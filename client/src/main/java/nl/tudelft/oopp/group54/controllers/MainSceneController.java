package nl.tudelft.oopp.group54.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import nl.tudelft.oopp.group54.views.MainView;

public class MainSceneController {

    /**
     * Handles clicking the button.
     * @throws IOException 
     */
    public void createButtonClicked() throws IOException {
    	Parent part = FXMLLoader.load(getClass().getResource("/dateTimeScene.fxml"));
        Scene scene = new Scene(part);
        MainView.changeScene(scene);
    }
    
    public void joinButtonClicked() throws IOException {
    	Parent part = FXMLLoader.load(getClass().getResource("/lectureRoomScene.fxml"));
        Scene scene = new Scene(part);
        MainView.changeScene(scene);
    }
}
