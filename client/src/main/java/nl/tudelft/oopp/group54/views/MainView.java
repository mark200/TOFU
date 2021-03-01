package nl.tudelft.oopp.group54.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {
	public static Stage stage;

	
	//CLEAN UP
    @Override
    public void start(Stage primaryStage) throws IOException {
    	stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("test 1");
        primaryStage.show();
    }
    
    public static void changeScene(Scene scene) {
    	stage.setScene(scene);
    	stage.setTitle("test 2");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
