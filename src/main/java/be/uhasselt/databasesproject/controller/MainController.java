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

    @FXML
    void initialize() {
        runnersButton.setOnAction(event -> showAdminPanel("runners"));
    }

    private void showAdminPanel(final String string) {
        final String resourceName = "/fxml/" + string + ".fxml";
        try {
            final Stage stage = new Stage();
            final AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resourceName)));
            final Scene scene = new Scene(root);
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
