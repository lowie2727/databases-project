package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditSegmentController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.SegmentJdbi;
import be.uhasselt.databasesproject.model.Segment;
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

public class SegmentController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Segment> tableView;

    @FXML
    private TableColumn<Segment, Integer> idTableColumn;

    @FXML
    private TableColumn<Segment, Integer> raceIdTableColumn;

    @FXML
    private TableColumn<Segment, String> locationTableColumn;

    @FXML
    private TableColumn<Segment, Integer> distanceTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editSegment(false));
        editButton.setOnAction(event -> editSegment(true));
        deleteButton.setOnAction(event -> deleteSegment());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadSegments();
    }

    private void initColumns() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        raceIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
    }

    private void loadSegments() {
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
        List<Segment> segments = segmentJdbi.getAll();
        tableView.getItems().setAll(segments);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private Segment getSelectedSegment() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editSegment(boolean isEdit) {
        Segment segment;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            segment = getSelectedSegment();
            title = "edit segment";
        } else {
            segment = new Segment(-1, -1, "", -1);
            title = "add segment";
        }

        String resourceName = "/fxml/admin/edit/editSegment.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setSegmentScreen(anchorPane, loader, segment, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setSegmentScreen(AnchorPane anchorPane, FXMLLoader loader, Segment segment, String title) {
        EditSegmentController editSegmentController = loader.getController();
        editSegmentController.inflateUI(segment);
        editSegmentController.setAdminMode();

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadSegments());
    }

    private void deleteSegment() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this segment? This action cannot be undone.");
            if (confirmationDelete) {
                SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
                segmentJdbi.delete(getSelectedSegment());
                loadSegments();
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
