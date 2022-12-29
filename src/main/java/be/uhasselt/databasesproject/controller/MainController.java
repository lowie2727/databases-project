package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button runnerRegistrationButton;

    @FXML
    private Button volunteerRegistrationButton;

    @FXML
    void initialize() {
        adminButton.setOnAction(event -> showPanel("admin", true));
        runnerRegistrationButton.setOnAction(event -> showPanel("registerRunner", false));
    }

    private void showPanel(String string, boolean isAdmin) {
        String resourceName;
        if (isAdmin) {
            resourceName = "/fxml/admin/" + string + ".fxml";
        } else {
            resourceName = "/fxml/user/" + string + ".fxml";
        }

        try {
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resourceName)));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(string);
            stage.initOwner(Main.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }
}
