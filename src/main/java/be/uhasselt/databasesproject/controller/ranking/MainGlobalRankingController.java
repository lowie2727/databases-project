package be.uhasselt.databasesproject.controller.ranking;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.GlobalRankingJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.GlobalRanking;
import be.uhasselt.databasesproject.model.Runner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MainGlobalRankingController {

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<GlobalRanking, Integer> prizeMoneyTableColumn;

    @FXML
    private TableColumn<GlobalRanking, Integer> runnerIdTableColumn;

    @FXML
    private TableView<GlobalRanking> tableView;

    @FXML
    private TableColumn<GlobalRanking, Integer> averageSpeedTableColumn;

    @FXML
    void initialize() {
        initTable();
        closeButton.setOnAction(event -> SwitchAnchorPane.goToRankingMenu());
    }

    private void initTable() {
        initColumns();
        loadGlobalRanking();
    }

    private void initColumns() {
        runnerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        prizeMoneyTableColumn.setCellValueFactory(new PropertyValueFactory<>("prizeMoney"));
        averageSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<>("averageSpeed"));
    }

    private void loadGlobalRanking() {
        GlobalRankingJdbi globalRankingJdbi = new GlobalRankingJdbi(ConnectionManager.CONNECTION_STRING);

        List<Runner> runners = getAllRunners();
        for (Runner r : runners) {
            globalRankingJdbi.setAverageSpeedRunner(r.getId());
        }

        globalRankingJdbi.calculatePrizeMoney();

        List<GlobalRanking> globalRanking = globalRankingJdbi.getAllRanked();
        tableView.getItems().setAll(globalRanking);
    }

    private List<Runner> getAllRunners() {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        return runnerJdbi.getAll();
    }

}
