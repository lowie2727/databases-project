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
    private Button runnerAddButton;

    @FXML
    private Button runnerCloseButton;

    @FXML
    private Button runnerDeleteButton;

    @FXML
    private Button runnerEditButton;

    @FXML
    private TableView<Runner> runnerTableView;

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

    @FXML
    void initialize() {
        initTable();

        runnerAddButton.setOnAction(event -> addRunner());
        runnerEditButton.setOnAction(event -> editRunner());
        runnerDeleteButton.setOnAction(event -> deleteRunner());
        runnerCloseButton.setOnAction(event -> close());
    }

    private void initTable() {
        runnerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        runnerTableView.setEditable(true);

        initColumns();
        loadRunnersFromDatabase();
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

    public void loadRunnersFromDatabase() {
        final RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        final List<Runner> runners = runnerJdbi.getRunners();
        runnerTableView.getItems().setAll(runners);
    }

    private boolean verifyRowSelected() {
        if (runnerTableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private Runner getSelectedRunner() {
        return runnerTableView.getSelectionModel().getSelectedItem();
    }

    private void addRunner() {
        //TODO
    }

    private void editRunner() {
        if (verifyRowSelected()) {
            final String resourceName = "/fxml/editRunner.fxml";
            try {
                final Stage stage = new Stage();
                final FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
                final AnchorPane root = loader.load();

                final EditRunnerController controller = loader.getController();
                controller.inflateUI(getSelectedRunner());

                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("edit runner");
                stage.initOwner(Main.getRootStage());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    loadRunnersFromDatabase();
                });
            } catch (Exception e) {
                throw new RuntimeException("Cannot find " + resourceName, e);
            }
        }
    }

    private void deleteRunner() {
        //TODO
    }

    private void close() {
        runnerCloseButton.getScene().getWindow().hide();
    }

    private void showAlert(final String title, final String content) {
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
