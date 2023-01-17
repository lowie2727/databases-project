package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditRunnerRaceController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.RunnerRace;
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

public class RunnerRaceController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<RunnerRace> tableView;

    @FXML
    private TableColumn<RunnerRace, Integer> runnerIdTableColumn;

    @FXML
    private TableColumn<RunnerRace, Integer> raceIdTableColumn;

    @FXML
    private TableColumn<RunnerRace, Integer> shirtNumberTableColumn;

    @FXML
    private TableColumn<RunnerRace, Integer> timeTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editRunnerRace(false));
        editButton.setOnAction(event -> editRunnerRace(true));
        deleteButton.setOnAction(event -> deleteRunnerRace());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadRunnerRaces();
    }

    private void initColumns() {
        runnerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        raceIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        shirtNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("shirtNumber"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
    }

    private void loadRunnerRaces() {
        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<RunnerRace> runnerRaces = runnerRaceJdbi.getAll();
        tableView.getItems().setAll(runnerRaces);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private RunnerRace getSelectedRunnerRace() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editRunnerRace(boolean isEdit) {
        RunnerRace runnerRace;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            runnerRace = getSelectedRunnerRace();
            title = "edit RunnerRace";
        } else {
            runnerRace = new RunnerRace(-1, -1, -1, -1);
            title = "add RunnerRace";
        }

        String resourceName = "/fxml/admin/edit/editRunnerRace.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setEditRaceScreen(anchorPane, loader, runnerRace, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setEditRaceScreen(AnchorPane anchorPane, FXMLLoader loader, RunnerRace runnerRace, String title) {
        EditRunnerRaceController editRunnerRaceController = loader.getController();

        if (runnerRace.getRunnerId() == -1) {
            editRunnerRaceController.setAdd();
        } else {
            editRunnerRaceController.setEdit();
        }
        editRunnerRaceController.inflateUI(runnerRace);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadRunnerRaces());
    }

    private void deleteRunnerRace() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this runnerRace? This action cannot be undone.");
            if (confirmationDelete) {
                RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
                runnerRaceJdbi.delete(getSelectedRunnerRace());
                loadRunnerRaces();
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
