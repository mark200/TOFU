package nl.tudelft.oopp.group54.views;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.oopp.group54.controllers.AbstractApplicationController;

public class MainView extends Application {

    private static Map<ApplicationScene, URL> sceneGraphMap;
    private static Stage stage;
    private static Scene rootScene;
    private static Stack<ApplicationScene> history;

    private static ApplicationScene currentScene;

    /**
     * Instantiates a new Main view.
     */
    public MainView() {
        sceneGraphMap = new HashMap<ApplicationScene, URL>();
        history = new Stack<>();
        try {
            this.batchLoadAllSceneLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go back once in history.
     */
    public static void goBackOnceInHistory() {
        if (!history.empty()) {
            currentScene = history.pop();
            changeScene(currentScene, false);
        }
    }

    /**
     * Change scene.
     *
     * @param newSceneName    the new scene name
     * @param recordInHistory the record in history
     */
    public static void changeScene(ApplicationScene newSceneName, boolean recordInHistory) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(sceneGraphMap.get(newSceneName));

        Parent root = null;
        try {
            root = loader.load();
            root.getStylesheets().add("stylesheets/defaultTheme.css");
        } catch (IOException e) {
            e.printStackTrace();
        }


        rootScene = new Scene(root);
        stage.setScene(rootScene);

        if (newSceneName.getText().equals("lectureRoomScene")) {
            stage.setHeight(650);
            stage.setWidth(765);
        }

        if (recordInHistory) {
            history.push(currentScene);
            currentScene = newSceneName;
        }

        AbstractApplicationController controller = loader.getController();
        controller.performControllerSpecificSetup();
    }

    /** Clear the history stack before changing scene, also display message "The lecture has ended."
     * @param newSceneName - ApplicationScene. The name of the scene to be switched to.
     * @param recordInHistory - Boolean whether record the last scene in the history or not.
     * @param clearHistory - Boolean whether to clear history or not.
     */
    public static void changeSceneClearHistory(ApplicationScene newSceneName, boolean recordInHistory, boolean clearHistory) {
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

        if (recordInHistory) {
            history.push(currentScene);
            currentScene = newSceneName;
        }

        if (clearHistory) {
            history.clear();
        }

        AbstractApplicationController controller = loader.getController();
        controller.performControllerSpecificSetup();
        // switch from main from lecture.
        controller.switchToMainFromLecture();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void batchLoadAllSceneLocations() throws IOException {
        URL xmlUrl;

        for (ApplicationScene sc : ApplicationScene.values()) {
            xmlUrl = getClass().getResource("/" + sc.toString() + ".fxml");
            sceneGraphMap.put(sc, xmlUrl);
        }
    }

    /**
     *  starts the main scene.
     * @param primaryStage the primary stage
     */
    public void start(Stage primaryStage) {
        stage = primaryStage;
        currentScene = ApplicationScene.MAINVIEW;
        changeScene(ApplicationScene.MAINVIEW, false);
        stage.show();
    }
}
