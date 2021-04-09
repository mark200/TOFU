package nl.tudelft.oopp.group54.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import nl.tudelft.oopp.group54.Datastore;

public class SettingsViewController extends AbstractApplicationController {

    @FXML
    TextField serverInstanceEndpointPrompt;

    @FXML
    Button serverInstanceEndpointButton;

    @FXML
    ComboBox<String> colorSchemeComboBox;

    private Datastore ds;

    @Override
    public void performControllerSpecificSetup() {

        this.ds = Datastore.getInstance();

        System.out.println("Settings controller config");

        serverInstanceEndpointPrompt.setText(Datastore.getInstance().getServiceEndpoint());

        colorSchemeComboBox.getItems().addAll(
                "Darcc",
                "Hackerman",
                "Light"
        );

        colorSchemeComboBox.setValue(this.ds.getColorScheme());
    }


    /**
     * This method will change the endpoint to which we make requests.
     */
    public void serverInstanceEndpointButtonClicked() {

        Datastore.getInstance().setServiceEndpoint(
                this.serverInstanceEndpointPrompt.getCharacters().toString()
        );
    }

    /**
     * This method will change the color scheme. Changes will be visible
     * after the current scene is changed.
     */
    public void colorSchemeComboBoxChanged() {
        Datastore.getInstance().setColorScheme(
                this.colorSchemeComboBox.getValue()
        );
    }

}
