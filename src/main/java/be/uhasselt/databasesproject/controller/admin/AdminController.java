package be.uhasselt.databasesproject.controller.admin;

import be.uhasselt.databasesproject.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class AdminController {

    @FXML
    private Button editRunnersButton;

    @FXML
    private Button editVolunteersButton;

    @FXML
    void initialize() {
        editRunnersButton.setOnAction(event -> showPanel("runner"));
        editVolunteersButton.setOnAction(event -> showPanel("race"));
    }

    private void showPanel(String string) {
        String resourceName = "/fxml/admin/" + string + ".fxml";
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
