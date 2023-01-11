package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.*;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Volunteer;
import be.uhasselt.databasesproject.model.VolunteerRace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class VolunteerUserController {
    @FXML
    private ChoiceBox<Race> choiceBox;

    @FXML
    private TableColumn<Race, String> dateTableColumn;

    @FXML
    private TableColumn<Race, Integer> distanceTableColumn;

    @FXML
    private Button editPersonalInfoButton;

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


    private Volunteer volunteer;
    private int id;

    @FXML
    void initialize() {
        errorText.setText("");
        choiceBoxSetup();

        logoutButton.setOnAction(event -> SwitchAnchorPane.goToMainMenu());
        registerForRaceButton.setOnAction(event -> registerForRace());
        signOutFromRaceButton.setOnAction(event -> signOutFromRace());
        editPersonalInfoButton.setOnAction(event -> showPanel("personalInfo"));
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
        //priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    private void loadRaces() {
        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = volunteerRaceJdbi.getRegisteredRaces(volunteer.getId());
        tableView.getItems().setAll(races);
    }

    private void registerForRace() {
        if (isChoiceBoxSelected()) {
            addVolunteerRaceToDatabase();
            loadRaces();
        }
    }

    private void addVolunteerRaceToDatabase() {
        errorText.setText("");

        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        int raceId = choiceBox.getValue().getId();
        VolunteerRace volunteerRace = new VolunteerRace(volunteer.getId(), raceId);

        try {
            volunteerRaceJdbi.insert(volunteerRace);
        } catch (Exception e) {
            choiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
            errorText.setText("Already registered for this race");
        }
    }

    private void signOutFromRace() {
        if (isChoiceBoxSelected()) {
            if (isVolunteerRegisteredForRace()) {
                removeVolunteerRaceFromDatabase();
                loadRaces();
            }
        }
    }

    private boolean isVolunteerRegisteredForRace() {
        errorText.setText("");

        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        int raceId = choiceBox.getValue().getId();

        try {
            volunteerRaceJdbi.getVolunteerRaceById(volunteer.getId(), raceId);
            return true;
        } catch (Exception e) {
            choiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
            errorText.setText("Not registered for this race");
            return false;
        }
    }

    private void removeVolunteerRaceFromDatabase() {
        errorText.setText("");

        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        int raceId = choiceBox.getValue().getId();
        volunteerRaceJdbi.delete(volunteer.getId(), raceId);
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
        id = volunteer.getId();

        if (Objects.equals(string, "personalInfo")) {
            SwitchAnchorPane.goToEditVolunteer(id);
        }
    }

    public void inflateUI(Volunteer volunteer) {
        this.volunteer = volunteer;
        id = volunteer.getId();

        tableViewSetup();

        nameText.setText(volunteer.getFirstName() + " " + volunteer.getFamilyName());
    }
}
