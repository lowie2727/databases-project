package be.uhasselt.databasesproject.controller.ranking;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RankingMenuController {

    @FXML
    private Button closeButton;

    @FXML
    private Button globalRankingButton;

    @FXML
    private Button raceRankingButton;

    @FXML
    void initialize() {
        globalRankingButton.setOnAction(event -> SwitchAnchorPane.goToRanking(true, null));
        raceRankingButton.setOnAction(event -> SwitchAnchorPane.goToRaceRankingMenu());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToMainMenu());
    }
}
