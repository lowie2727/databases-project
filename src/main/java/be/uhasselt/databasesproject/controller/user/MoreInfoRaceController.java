package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerSegmentJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.RunnerRace;
import be.uhasselt.databasesproject.model.RunnerSegment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class MoreInfoRaceController {

    @FXML
    private Text distanceText;

    @FXML
    private Text errorMessageText;

    @FXML
    private Text nameRunnerText;

    @FXML
    private Text nameRaceText;

    @FXML
    private Text originalDateText;

    @FXML
    private Text priceText;

    @FXML
    private Text timeText;

    @FXML
    private Text averageSpeedText;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<RunnerSegment> tableView;

    @FXML
    private TableColumn<RunnerSegment, String> locationTableColumn;

    @FXML
    private TableColumn<RunnerSegment, Integer> distanceTableColumn;

    @FXML
    private TableColumn<RunnerSegment, Integer> timeTableColumn;

    @FXML
    private TableColumn<RunnerSegment, Double> averageSpeedTableColumn;


    private Runner runner;
    private Race race;

    @FXML
    void initialize() {
        cancelButton.setOnAction(event -> SwitchAnchorPane.goToRunnerUser(runner.getId()));
        errorMessageText.setText("");
    }

    public void inflateUI(Race race, Runner runner) {
        this.runner = runner;
        this.race = race;
        setUpTableView();

        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        RunnerRace runnerRace = runnerRaceJdbi.getRunnerRaceById(runner.getId(), race.getId());

        nameRunnerText.setText(runner.getFirstName() + " " + runner.getFamilyName());
        originalDateText.setText(race.getDate());
        nameRaceText.setText(race.getName());
        distanceText.setText(race.getDistanceInKm() + " km");
        priceText.setText(Double.toString(race.getPrice()));

        if (runnerRace.getTime() == 0) {
            timeText.setText("tbd");
        } else {
            timeText.setText(runnerRace.getTimeString());
        }
        if (runnerRace.getTime() == 0) {
            averageSpeedText.setText("tbd");
        } else {
            averageSpeedText.setText(getAverageSpeed(runnerRace) + " km/h");
        }
    }

    public Double getAverageSpeed(RunnerRace runnerRace) {
        double averageSpeed = ((double) race.getDistance() / (double) runnerRace.getTime()) * 3.6;
        averageSpeed = Math.round(averageSpeed * 100);
        averageSpeed = averageSpeed / 100;

        return averageSpeed;
    }

    private void setUpTableView() {
        initColumns();
        loadRunnerSegments();
    }

    private void initColumns() {
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        averageSpeedTableColumn.setCellValueFactory(new PropertyValueFactory<>("averageSpeed"));
    }

    private void loadRunnerSegments() {
        RunnerSegmentJdbi runnerSegmentJdbi = new RunnerSegmentJdbi(ConnectionManager.CONNECTION_STRING);
        List<RunnerSegment> runnerSegments = runnerSegmentJdbi.getSegmentAndTimes(race.getId(), runner.getId());
        tableView.getItems().setAll(runnerSegments);
    }
}
