package nl.tudelft.oopp.group54.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import nl.tudelft.oopp.group54.views.MainView;

/**
 * The type Abstract application controller.
 */
public abstract class AbstractApplicationController {

    @FXML
    ToolBar utilityToolbar;

    @FXML
    Button utilityToolbarBackButton;

    @FXML
    Text systemStatus;

    RotateTransition rotateTransition;

    FadeTransition fadeTransition;

    public AbstractApplicationController() {

    }

    // FIXME: The way this would work is create a new Transition object for every call.
    //  Maybe we can somehow save them for future use, once created instead.

    /**
     * Shake widget.
     *
     * @param widget the widget
     * @param angle  the angle
     */
    public void shakeWidget(Node widget, Double angle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.1));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(angle);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);

        rotateTransition.setNode(widget);
        rotateTransition.play();
    }

    public void shakeWidget(Node widget) {
        this.shakeWidget(widget, 4.0);
    }


    /**
     * Fade widget out.
     *
     * @param widget the widget
     */
    public void fadeWidgetOut(Node widget) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3));
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(1);

        fadeTransition.setNode(widget);
        fadeTransition.play();
    }


    /**
     * Spin widget.
     *
     * @param widget the widget
     */
    public void spinWidget(Node widget) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1));
        rotateTransition.setToAngle(360);
        rotateTransition.setFromAngle(0);
        rotateTransition.setCycleCount(1);
        rotateTransition.setNode(widget);
        rotateTransition.play();
    }

    public void utilityToolbarBackButtonPressed() {
        MainView.goBackOnceInHistory();
    }

    public void displayStatusMessage(String message) {
        this.systemStatus.setText(message);
        this.fadeWidgetOut(this.systemStatus);
    }

    public abstract void performControllerSpecificSetup();

    /**
     * display the message that lecture has been ended when students gets kicked out of the room.
     */
    public void switchToMainFromLecture() {
        this.displayStatusMessage("The Lecture has ended.");
    }

}
