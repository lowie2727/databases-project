package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditVolunteerRaceController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.VolunteerRaceJdbi;
import be.uhasselt.databasesproject.model.VolunteerRace;
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

public class VolunteerRaceController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<VolunteerRace> tableView;

    @FXML
    private TableColumn<VolunteerRace, Integer> volunteerIdTableColumn;

    @FXML
    private TableColumn<VolunteerRace, Integer> raceIdTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editVolunteerRace());
        deleteButton.setOnAction(event -> deleteVolunteerRace());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadVolunteerRaces();
    }

    private void initColumns() {
        volunteerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("volunteerId"));
        raceIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("raceId"));
    }

    private void loadVolunteerRaces() {
        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<VolunteerRace> volunteerRaces = volunteerRaceJdbi.getAll();
        tableView.getItems().setAll(volunteerRaces);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private VolunteerRace getSelectedVolunteerRace() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editVolunteerRace() {
        VolunteerRace volunteerRace;
        String title;

        volunteerRace = new VolunteerRace(-1, -1);
        title = "add VolunteerRace";


        String resourceName = "/fxml/admin/edit/editVolunteerRace.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setVolunteerRaceScreen(anchorPane, loader, volunteerRace, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setVolunteerRaceScreen(AnchorPane anchorPane, FXMLLoader loader, VolunteerRace volunteerRace, String title) {
        EditVolunteerRaceController editVolunteerRaceController = loader.getController();
        editVolunteerRaceController.inflateUI(volunteerRace);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadVolunteerRaces());
    }

    private void deleteVolunteerRace() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this volunteerRace? This action cannot be undone.");
            if (confirmationDelete) {
                VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
                volunteerRaceJdbi.delete(getSelectedVolunteerRace());
                loadVolunteerRaces();
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
