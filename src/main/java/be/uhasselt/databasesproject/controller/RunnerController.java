package be.uhasselt.databasesproject.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RunnerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button runnerAddButton;

    @FXML
    private Button runnerCloseButton;

    @FXML
    private Button runnerDeleteButton;

    @FXML
    private Button runnerEditButton;

    @FXML
    private TableView<ObservableList<String>> runnerTableView;

    @FXML
    private Text runnerText;

    @FXML
    void initialize() {
        initTable();

        runnerAddButton.setOnAction(event -> addNewRow());
        runnerEditButton.setOnAction(event -> {
            verifyRowSelected();
            editRow();
        });
        runnerCloseButton.setOnAction(event -> close());
    }

    private void initTable() {
        runnerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        runnerTableView.getColumns().clear();

        setColumnNames();

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.ConnectionString);
        List<Runner> runners = runnerJdbi.getRunners();

        for (Runner r : runners) {
            runnerTableView.getItems().add(FXCollections.observableArrayList(r.getStringList()));
        }
    }

    private void setColumnNames() {
        int columnIndex = 0;
        for (var columnName : new String[]{"id", "firstName", "familyName", "age", "weight", "length", "streetName", "houseNumber", "boxNumber", "postalCode", "city", "country"}) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            final int finalColumnIndex = columnIndex;
            column.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColumnIndex)));
            runnerTableView.getColumns().add(column);
            columnIndex++;
        }
    }

    private void addNewRow() {
        //TODO
    }

    private void verifyRowSelected() {
        if (runnerTableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
        }
    }

    private void editRow() {
        //TODO
    }

    private void close() {
        Stage stage = (Stage) runnerCloseButton.getScene().getWindow();
        stage.close();
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
