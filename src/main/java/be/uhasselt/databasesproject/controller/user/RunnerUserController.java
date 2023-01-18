package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.RunnerRace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class RunnerUserController {

    @FXML
    private ChoiceBox<Race> choiceBox;

    @FXML
    private TableColumn<Race, String> dateTableColumn;

    @FXML
    private TableColumn<Race, Integer> distanceTableColumn;

    @FXML
    private Button editPersonalInfoButton;

    @FXML
    private Button moreInfoRaceButton;

    @FXML
    private Text errorText;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Race, String> nameTableColumn;

    @FXML
    private Text nameText;

    @FXML
    private TableColumn<Race, Double> priceTableColumn;

    @FXML
    private Button registerForRaceButton;

    @FXML
    private Button signOutFromRaceButton;

    @FXML
    private TableView<Race> tableView;


    private Runner runner;
    private int id;

    @FXML
    void initialize() {
        errorText.setText("");
        choiceBoxSetup();

        logoutButton.setOnAction(event -> SwitchAnchorPane.goToMainMenu());
        registerForRaceButton.setOnAction(event -> registerForRace());
        signOutFromRaceButton.setOnAction(event -> signOutFromRace());
        editPersonalInfoButton.setOnAction(event -> showPanel("personalInfo"));
        moreInfoRaceButton.setOnAction(event -> showPanel("moreInfoRace"));
    }

    private void choiceBoxSetup() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAllUpcoming();
        ObservableList<Race> observableRaces = FXCollections.observableList(races);
        choiceBox.setItems(observableRaces);
    }

    private void tableViewSetup() {
        initColumns();
        loadRaces();
    }

    private void initColumns() {
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadRaces() {
        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = runnerRaceJdbi.getRegisteredRaces(runner.getId());
        tableView.getItems().setAll(races);
    }

    private void registerForRace() {
        if (isChoiceBoxSelected()) {
            addRunnerRaceToDatabase();
            loadRaces();
        }
    }

    private void addRunnerRaceToDatabase() {
        errorText.setText("");

        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);

        int raceId = choiceBox.getValue().getId();
        int shirtNumber = runnerRaceJdbi.getNextShirtNumber(raceId);
        RunnerRace runnerRace = new RunnerRace(runner.getId(), raceId, shirtNumber, 0);

        try {
            runnerRaceJdbi.insert(runnerRace);
            runnerJdbi.insertIntoSegmentTimes(raceId, runner.getId());
        } catch (Exception e) {
            choiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
            errorText.setText("Already registered for this race");
        }
    }

    private void signOutFromRace() {
        if (isChoiceBoxSelected()) {
            if (isRunnerRegisteredForRace()) {
                removeRunnerRaceFromDatabase();
                loadRaces();
            }
        }
    }

    private boolean isRunnerRegisteredForRace() {
        errorText.setText("");

        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        int raceId = choiceBox.getValue().getId();

        try {
            runnerRaceJdbi.getRunnerRaceById(runner.getId(), raceId);
            return true;
        } catch (Exception e) {
            choiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
            errorText.setText("Not registered for this race");
            return false;
        }
    }

    private void removeRunnerRaceFromDatabase() {
        errorText.setText("");

        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);

        int raceId = choiceBox.getValue().getId();
        runnerRaceJdbi.delete(runner.getId(), raceId);
        runnerJdbi.deleteFromSegment(raceId, runner.getId());
    }

    private boolean isChoiceBoxSelected() {
        if (choiceBox.getValue() == null) {
            choiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
            errorText.setText("Please select a race");
            return false;
        }

        choiceBox.setBorder(Border.EMPTY);
        return true;
    }

    private void showPanel(String string) {
        Race race;
        id = runner.getId();

        if (Objects.equals(string, "personalInfo")) {
            SwitchAnchorPane.goToEditRunner(id);
        }
        if (Objects.equals(string, "moreInfoRace")) {
            if (!verifyRowSelected()) {
                return;
            } else {
                race = getSelectedRace();
                SwitchAnchorPane.goToMoreInfoRace(race.getId(), id);
            }
        }
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

    public void inflateUI(Runner runner) {
        this.runner = runner;
        id = runner.getId();

        tableViewSetup();

        nameText.setText(runner.getFirstName() + " " + runner.getFamilyName());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
