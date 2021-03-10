package nl.tudelft.oopp.group54.views;

import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {

  private static Map<ApplicationScene, URL> sceneGraphMap;
  private static Stage stage;
  private static Scene rootScene;
  private static Stack<ApplicationScene> history;

  private static ApplicationScene currentScene;

  public MainView() {
    sceneGraphMap = new HashMap<ApplicationScene, URL>();
    history = new Stack<>();
    try {
      this.batchLoadAllSceneLocations();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void batchLoadAllSceneLocations() throws IOException {
    URL xmlUrl;

    for(ApplicationScene sc: ApplicationScene.values()) {
      xmlUrl = getClass().getResource("/"+sc.toString()+".fxml");
      sceneGraphMap.put(sc, xmlUrl);
    }
  }

  public void start(Stage primaryStage) {
    stage = primaryStage;
    currentScene = ApplicationScene.MAINVIEW;
    changeScene(ApplicationScene.MAINVIEW, false);
    stage.show();
  }

  public static void goBackOnceInHistory() {
    if(!history.empty()) {
      currentScene = history.pop();
      changeScene(currentScene, false);
    }
  }

  public static void changeScene(ApplicationScene newSceneName, boolean recordInHistory) {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(sceneGraphMap.get(newSceneName));

    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    rootScene = new Scene(root);
    stage.setScene(rootScene);

    if(recordInHistory) {
      history.push(currentScene);
      currentScene = newSceneName;
    }

    AbstractApplicationController controller = loader.getController();
    controller.performControllerSpecificSetup();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
