package be.uhasselt.databasesproject.controller.ranking;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RaceRankingMenuController {

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Race, String> dateTableColumn;

    @FXML
    private TableColumn<Race, Integer> distanceTableColumn;

    @FXML
    private TableColumn<Race, String> nameTableColumn;

    @FXML
    private Button showButton;

    @FXML
    private TableView<Race> tableView;

    @FXML
    void initialize() {
        initTable();

        closeButton.setOnAction(event -> SwitchAnchorPane.goToRankingMenu());
        showButton.setOnAction(event -> show());
    }

    private void show() {
        if (verifyRowSelected()) {
            SwitchAnchorPane.goToShowRaceRanking(getSelectedRace());
        }
    }

    private Race getSelectedRace() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private void initTable() {
        initColumns();
        loadRaces();
    }

    private void initColumns() {
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
    }

    private void loadRaces() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAllPrevious();
        tableView.getItems().setAll(races);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
