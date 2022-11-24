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
    private Button buttonTest;

    public void initialize() {
        buttonTest.setOnAction(event -> showAdminPanel("runners"));
    }

    private void showAdminPanel(String string) {
        String resourceName = "be/uhasselt/databasesproject/" + string + ".fxml";
        try {
            var stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane root = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(resourceName)));
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
