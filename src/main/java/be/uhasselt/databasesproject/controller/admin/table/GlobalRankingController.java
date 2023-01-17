package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.GlobalRankingJdbi;
import be.uhasselt.databasesproject.model.GlobalRanking;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GlobalRankingController {

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<GlobalRanking> tableView;

    @FXML
    private TableColumn<GlobalRanking, Integer> runnerIdTableColumn;

    @FXML
    private TableColumn<GlobalRanking, Integer> prizeMoneyTableColumn;

    @FXML
    private TableColumn<GlobalRanking, Integer> averageSpeedTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        deleteButton.setOnAction(event -> deleteGlobalRanking());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadGlobalRankings();
    }

    private void initColumns() {
        runnerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        prizeMoneyTableColumn.setCellValueFactory(new PropertyValueFactory<>("prizeMoney"));
        averageSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<>("averageSpeed"));
    }

    private void loadGlobalRankings() {
        GlobalRankingJdbi globalRankingJdbi = new GlobalRankingJdbi(ConnectionManager.CONNECTION_STRING);
        List<GlobalRanking> globalRankings = globalRankingJdbi.getAllRanked();
        tableView.getItems().setAll(globalRankings);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private GlobalRanking getSelectedGlobalRanking() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void deleteGlobalRanking() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this globalRanking? This action cannot be undone.");
            if (confirmationDelete) {
                GlobalRankingJdbi globalRankingJdbi = new GlobalRankingJdbi(ConnectionManager.CONNECTION_STRING);
                globalRankingJdbi.delete(getSelectedGlobalRanking());
                loadGlobalRankings();
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlertDelete(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(buttonType -> confirmationDelete = true);
    }
}
