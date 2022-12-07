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
    private Button runnersButton;

    public void initialize() {
        runnersButton.setOnAction(event -> showAdminPanel("runners"));
    }

    private void showAdminPanel(String string) {
        String resourceName = "/fxml/" + string + ".fxml";
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
