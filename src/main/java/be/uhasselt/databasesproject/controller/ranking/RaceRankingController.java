package be.uhasselt.databasesproject.controller.ranking;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RankingJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.RunnerRanking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Collections;
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
    private TableColumn<RunnerRanking, Double> prizeTableColumn;

    @FXML
    private TableColumn<RunnerRanking, Integer> rankTableColumn;

    @FXML
    private TableView<RunnerRanking> tableView;

    @FXML
    private TableColumn<RunnerRanking, Integer> totalTimeTableColumn;

    private Race race;
    private boolean isGlobalRanking;

    @FXML
    void initialize() {
    }

    private void initColumns() {
        rankTableColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        totalTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        averageSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<>("averageSpeed"));
        prizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("prizeMoney"));

        prizeTableColumn.setVisible(isGlobalRanking);
    }

    public void load() {
        initColumns();
        setTableView();

        if (isGlobalRanking) {
            closeButton.setOnAction(event -> SwitchAnchorPane.goToRankingMenu());
        } else {
            closeButton.setOnAction(event -> SwitchAnchorPane.goToRaceRankingMenu());
        }
    }

    private void setTableView() {
        RankingJdbi rankingJdbi = new RankingJdbi(ConnectionManager.CONNECTION_STRING);

        if (isGlobalRanking) {
            setGlobalRankingTableView(rankingJdbi);
        } else {
            setRaceRankingTableView(rankingJdbi);
        }
    }

    private void setGlobalRankingTableView(RankingJdbi rankingJdbi) {
        List<Integer> prizeMoney = new ArrayList<>();
        Collections.addAll(prizeMoney, 100, 50, 20, 10, 5);
        int index = 0;
        int size = prizeMoney.size();

        List<RunnerRanking> ranking = rankingJdbi.getGlobalRanking();

        for (RunnerRanking r : ranking) {
            if (index < size) {
                r.setPrizeMoney(prizeMoney.get(index));
            }
            index++;
            r.setRank(index);
        }
        tableView.getItems().setAll(ranking);
    }

    private void setRaceRankingTableView(RankingJdbi rankingJdbi) {
        int index = 0;
        List<RunnerRanking> ranking = rankingJdbi.getRaceRanking(race.getId());

        for (RunnerRanking r : ranking) {
            index++;
            r.setRank(index);
        }
        tableView.getItems().setAll(ranking);
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setGlobalRanking() {
        isGlobalRanking = true;
    }

    public void setRaceRanking() {
        isGlobalRanking = false;
    }
}