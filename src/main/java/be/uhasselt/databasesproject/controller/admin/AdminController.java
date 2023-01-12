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
    private Button editSegmentsButton;

    @FXML
    private Button editRunnerRaceButton;

    @FXML
    private Button editSegmentTimeButton;

    @FXML
    private Button editVolunteerRaceButton;

    @FXML
    private Button editGlobalRankingButton;

    @FXML
    void initialize() {
        editRunnersButton.setOnAction(event -> showPanel("runner"));
        editVolunteersButton.setOnAction(event -> showPanel("volunteer"));
        editRacesButton.setOnAction(event -> showPanel("race"));
        editSegmentsButton.setOnAction(event -> showPanel("segment"));
        editRunnerRaceButton.setOnAction(event -> showPanel("runnerRace"));
        editSegmentTimeButton.setOnAction(event -> showPanel("segmentTime"));
        editVolunteerRaceButton.setOnAction(event -> showPanel("volunteerRace"));
        editGlobalRankingButton.setOnAction(event -> showPanel("globalRanking"));
        mainMenuButton.setOnAction(event -> SwitchAnchorPane.goToMainMenu());
    }

    private void showPanel(String string) {
        if (Objects.equals(string, "runner")) {
            SwitchAnchorPane.goToRunner();
        } else if (Objects.equals(string, "volunteer")) {
            SwitchAnchorPane.goToVolunteer();
        } else if (Objects.equals(string, "race")) {
            SwitchAnchorPane.goToRace();
        } else if (Objects.equals(string, "segment")) {
            SwitchAnchorPane.goToSegment();
        } else if (Objects.equals(string, "runnerRace")) {
            SwitchAnchorPane.goToRunnerRace();
        } else if (Objects.equals(string, "segmentTime")) {
            SwitchAnchorPane.goToSegmentTime();
        } else if (Objects.equals(string, "volunteerRace")) {
            SwitchAnchorPane.goToVolunteerRace();
        } else if (Objects.equals(string, "globalRanking")) {
            SwitchAnchorPane.goToGlobalRankingAdmin();
        }
    }
}
