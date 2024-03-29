package be.uhasselt.databasesproject.controller.admin.table;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.controller.admin.edit.EditRaceController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class RaceController {

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Race, String> dateTableColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Race, Integer> distanceTableColumn;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Race, Integer> idTableColumn;

    @FXML
    private TableColumn<Race, String> nameTableColumn;

    @FXML
    private TableColumn<Race, Double> priceTableColumn;

    @FXML
    private TableView<Race> tableView;

    private boolean confirmationDelete = false;

    @FXML
    void initialize() {
        initTable();

        addButton.setOnAction(event -> editRace(false));
        editButton.setOnAction(event -> editRace(true));
        deleteButton.setOnAction(event -> deleteRace());
        closeButton.setOnAction(event -> SwitchAnchorPane.goToAdmin());
    }

    private void initTable() {
        initColumns();
        loadRaces();
    }

    private void initColumns() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distanceInKm"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadRaces() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAll();
        tableView.getItems().setAll(races);
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private Race getSelectedRace() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void editRace(boolean isEdit) {
        Race race;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            race = getSelectedRace();
            title = "edit race";
        } else {
            race = new Race(-1, "", "", -1, -1.0);
            title = "add race";
        }

        String resourceName = "/fxml/admin/edit/editRace.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setEditRaceScreen(anchorPane, loader, race, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setEditRaceScreen(AnchorPane anchorPane, FXMLLoader loader, Race race, String title) {
        EditRaceController editRaceController = loader.getController();
        editRaceController.inflateUI(race);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        editRaceController.setStage(stage);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadRaces());
    }

    private void deleteRace() {
        if (verifyRowSelected()) {
            showAlertDelete("Warning", "Are you sure you want to delete this race? This action cannot be undone.");
            if (confirmationDelete) {
                RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
                raceJdbi.delete(getSelectedRace());
                loadRaces();
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
