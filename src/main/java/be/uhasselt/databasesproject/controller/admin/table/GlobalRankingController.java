package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditGlobalRankingController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.GlobalRankingJdbi;
import be.uhasselt.databasesproject.model.GlobalRanking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class GlobalRankingController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<GlobalRanking> tableView;

    @FXML
    private TableColumn<GlobalRanking, Integer> runnerIdTableColumn;

    @FXML
    private TableColumn<GlobalRanking, Integer> prizeMoneyTableColumn;

    @FXML
    private TableColumn<GlobalRanking, Integer> totalTimeTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editGlobalRanking(false));
        editButton.setOnAction(event -> editGlobalRanking(true));
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
        totalTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
    }

    private void loadGlobalRankings() {
        GlobalRankingJdbi globalRankingJdbi = new GlobalRankingJdbi(ConnectionManager.CONNECTION_STRING);
        List<GlobalRanking> globalRankings = globalRankingJdbi.getAll();
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

    private void editGlobalRanking(boolean isEdit) {
        GlobalRanking globalRanking;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            globalRanking = getSelectedGlobalRanking();
            title = "edit GlobalRanking";
        } else {
            globalRanking = new GlobalRanking(-1, -1.0, -1);
            title = "add GlobalRanking";
        }

        String resourceName = "/fxml/admin/edit/editGlobalRanking.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setGlobalRankingScreen(anchorPane, loader, globalRanking, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setGlobalRankingScreen(AnchorPane anchorPane, FXMLLoader loader, GlobalRanking globalRanking, String title) {
        EditGlobalRankingController editGlobalRankingController = loader.getController();
        editGlobalRankingController.inflateUI(globalRanking);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadGlobalRankings());
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
