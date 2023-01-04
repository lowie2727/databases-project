package be.uhasselt.databasesproject.controller.admin;

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
    private Button editRacesButton;

    private Stage stage;

    @FXML
    void initialize() {
        editRunnersButton.setOnAction(event -> showPanel("runner"));
        editVolunteersButton.setOnAction(event -> showPanel("volunteer"));
        editRacesButton.setOnAction(event -> showPanel("race"));
    }

    private void showPanel(String string) {
        String resourceName = "/fxml/admin/" + string + ".fxml";

        RunnerController runnerController;
        VolunteerController volunteerController;
        RaceController raceController;

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane root = loader.load();

            if (Objects.equals(string, "runner")) {
                runnerController = loader.getController();
                runnerController.setStage(stage);
            } else if (Objects.equals(string, "volunteer")) {
                volunteerController = loader.getController();
                volunteerController.setStage(stage);
            } else if (Objects.equals(string, "race")) {
                raceController = loader.getController();
                raceController.setStage(stage);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(string);
            stage.initOwner(this.stage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
