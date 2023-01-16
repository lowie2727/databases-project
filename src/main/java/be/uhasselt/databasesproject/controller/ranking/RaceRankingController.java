package be.uhasselt.databasesproject.controller.ranking;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RankingJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.RunnerRanking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RaceRankingController {

    @FXML
    private TableColumn<RunnerRanking, Double> averageSpeedTableColumn;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<RunnerRanking, String> firstNameTableColumn;

    @FXML
    private TableColumn<RunnerRanking, String> lastNameTableColumn;

    @FXML
    private TableView<RunnerRanking> tableView;

    @FXML
    private TableColumn<RunnerRanking, Integer> timeTableColumn;

    private Race race;

    @FXML
    void initialize() {
        initColumns();
        closeButton.setOnAction(event -> SwitchAnchorPane.goToRaceRankingMenu());
    }

    private void initColumns() {
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        averageSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<>("averageSpeed"));
    }

    public void load() {
        RankingJdbi rankingJdbi = new RankingJdbi(ConnectionManager.CONNECTION_STRING);
        List<RunnerRanking> ranking = rankingJdbi.getTimesFromRace(race.getId());
        calculateAverageSpeed(ranking);
        tableView.getItems().setAll(ranking);
    }

    private void calculateAverageSpeed(List<RunnerRanking> runnerRanking) {
        for (RunnerRanking r : runnerRanking) {
            double time = r.getTime();
            double distance = race.getDistance();

            double averageSpeed = (distance / time) * 3.6;
            averageSpeed = Math.round(averageSpeed * 10);
            averageSpeed = averageSpeed / 10;

            r.setAverageSpeed(averageSpeed);
        }
    }

    public void setRace(Race race) {
        this.race = race;
    }
}