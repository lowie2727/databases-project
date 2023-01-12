package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditVolunteerController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.VolunteerJdbi;
import be.uhasselt.databasesproject.model.Volunteer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class VolunteerController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableView<Volunteer> tableView;

    @FXML
    private TableColumn<Volunteer, Integer> idTableColumn;

    @FXML
    private TableColumn<Volunteer, String> firstNameTableColumn;

    @FXML
    private TableColumn<Volunteer, String> familyNameTableColumn;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editVolunteer(false));
        editButton.setOnAction(event -> editVolunteer(true));
        deleteButton.setOnAction(event -> deleteVolunteer());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadVolunteers();
    }

    private void initColumns() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        familyNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("familyName"));
    }

    private void loadVolunteers() {
        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        List<Volunteer> volunteers = volunteerJdbi.getAll();
        tableView.getItems().setAll(volunteers);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private Volunteer getSelectedVolunteer() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editVolunteer(boolean isEdit) {
        Volunteer volunteer;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            volunteer = getSelectedVolunteer();
            title = "edit volunteer";
        } else {
            volunteer = new Volunteer(-1, "", "", "");
            title = "add volunteer";
        }

        String resourceName = "/fxml/admin/edit/editVolunteer.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setEditVolunteerScreen(anchorPane, loader, volunteer, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setEditVolunteerScreen(AnchorPane anchorPane, FXMLLoader loader, Volunteer volunteer, String title) {
        EditVolunteerController editVolunteerController = loader.getController();
        editVolunteerController.inflateUI(volunteer);
        editVolunteerController.setAdminMode();

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadVolunteers());
    }

    private void deleteVolunteer() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this volunteer? This action cannot be undone.");
            if (confirmationDelete) {
                VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
                volunteerJdbi.delete(getSelectedVolunteer());
                loadVolunteers();
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
