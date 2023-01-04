package be.uhasselt.databasesproject.controller.admin;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Objects;

public class AdminController {

    @FXML
    private Button editRacesButton;

    @FXML
    private Button editRunnersButton;

    @FXML
    private Button editVolunteersButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    void initialize() {
        editRunnersButton.setOnAction(event -> showPanel("runner"));
        editVolunteersButton.setOnAction(event -> showPanel("volunteer"));
        editRacesButton.setOnAction(event -> showPanel("race"));
        mainMenuButton.setOnAction(event -> SwitchAnchorPane.goToMainMenu());
    }

    private void showPanel(String string) {
        if (Objects.equals(string, "runner")) {
            SwitchAnchorPane.goToRunner();
        } else if (Objects.equals(string, "volunteer")) {
            SwitchAnchorPane.goToVolunteer();
        } else if (Objects.equals(string, "race")) {
            SwitchAnchorPane.goToRace();
        }
    }
}
