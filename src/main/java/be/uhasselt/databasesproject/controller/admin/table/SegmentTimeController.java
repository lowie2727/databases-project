package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditSegmentTimeController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.SegmentTimesJdbi;
import be.uhasselt.databasesproject.model.SegmentTimes;
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

public class SegmentTimeController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<SegmentTimes> tableView;

    @FXML
    private TableColumn<SegmentTimes, Integer> runnerIdTableColumn;

    @FXML
    private TableColumn<SegmentTimes, Integer> segmentIdTableColumn;

    @FXML
    private TableColumn<SegmentTimes, Integer> timeTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editSegmentTime(false));
        editButton.setOnAction(event -> editSegmentTime(true));
        deleteButton.setOnAction(event -> deleteSegmentTime());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadSegmentTimes();
    }

    private void initColumns() {
        segmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("segmentId"));
        runnerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
    }

    private void loadSegmentTimes() {
        SegmentTimesJdbi segmentTimesJdbi = new SegmentTimesJdbi(ConnectionManager.CONNECTION_STRING);
        List<SegmentTimes> segmentTimes = segmentTimesJdbi.getAll();
        tableView.getItems().setAll(segmentTimes);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private SegmentTimes getSelectedSegmentTime() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editSegmentTime(boolean isEdit) {
        SegmentTimes segmentTimes;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            segmentTimes = getSelectedSegmentTime();
            title = "edit SegmentTime";
        } else {
            segmentTimes = new SegmentTimes(-1, -1, -1);
            title = "add SegmentTime";
        }

        String resourceName = "/fxml/admin/edit/editSegmentTime.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setSegmentTimesScreen(anchorPane, loader, segmentTimes, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setSegmentTimesScreen(AnchorPane anchorPane, FXMLLoader loader, SegmentTimes segmentTimes, String title) {
        EditSegmentTimeController editSegmentTimeController = loader.getController();
        if (segmentTimes.getRunnerId() == -1) {
            editSegmentTimeController.setAdd();
        } else {
            editSegmentTimeController.setEdit();
        }
        editSegmentTimeController.inflateUI(segmentTimes);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadSegmentTimes());
    }

    private void deleteSegmentTime() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this segmentTime? This action cannot be undone.");
            if (confirmationDelete) {
                SegmentTimesJdbi segmentTimesJdbi = new SegmentTimesJdbi(ConnectionManager.CONNECTION_STRING);
                segmentTimesJdbi.delete(getSelectedSegmentTime());
                loadSegmentTimes();
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
