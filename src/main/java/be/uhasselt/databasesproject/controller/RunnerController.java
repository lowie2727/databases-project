package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
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

public class RunnerController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Runner> tableView;

    @FXML
    private TableColumn<Runner, Integer> ageTableColumn;

    @FXML
    private TableColumn<Runner, String> boxNumberTableColumn;

    @FXML
    private TableColumn<Runner, String> cityTableColumn;

    @FXML
    private TableColumn<Runner, String> countryTableColumn;

    @FXML
    private TableColumn<Runner, String> familyNameTableColumn;

    @FXML
    private TableColumn<Runner, String> firstNameTableColumn;

    @FXML
    private TableColumn<Runner, String> houseNumberTableColumn;

    @FXML
    private TableColumn<Runner, Integer> idTableColumn;

    @FXML
    private TableColumn<Runner, Double> lengthTableColumn;

    @FXML
    private TableColumn<Runner, String> postalCodeTableColumn;

    @FXML
    private TableColumn<Runner, String> streetNameTableColumn;

    @FXML
    private TableColumn<Runner, Double> weightTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editRunner(false));
        editButton.setOnAction(event -> editRunner(true));
        deleteButton.setOnAction(event -> deleteRunner());
        closeButton.setOnAction(event -> close());
    }

    private void initTable() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setEditable(true);

        initColumns();
        loadRunners();
    }

    private void initColumns() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        familyNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        weightTableColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        lengthTableColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        streetNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("streetName"));
        houseNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        boxNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("boxNumber"));
        postalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityTableColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    private void loadRunners() {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        List<Runner> runners = runnerJdbi.getAll();
        tableView.getItems().setAll(runners);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private Runner getSelectedRunner() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editRunner(boolean isEdit) {
        Runner runner;
        String title;
        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            runner = getSelectedRunner();
            title = "edit runner";
        } else {
            runner = new Runner(-1, "", "", -1, -1.0, -1.0, "", "", "", "", "", "");
            title = "add runner";
        }

        String resourceName = "/fxml/editRunner.fxml";

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane root = loader.load();

            EditRunnerController controller = loader.getController();
            controller.inflateUI(runner);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.initOwner(Main.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(event -> {
                loadRunners();
            });
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void deleteRunner() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this runner? This action cannot be undone.");
            if (confirmationDelete) {
                RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
                runnerJdbi.delete(getSelectedRunner());
                loadRunners();
            }
        }
    }

    private void close() {
        closeButton.getScene().getWindow().hide();
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
