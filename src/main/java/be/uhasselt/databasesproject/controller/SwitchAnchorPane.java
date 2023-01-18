package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.admin.LoginAdminController;
import be.uhasselt.databasesproject.controller.admin.edit.EditRunnerController;
import be.uhasselt.databasesproject.controller.admin.edit.EditVolunteerController;
import be.uhasselt.databasesproject.controller.ranking.RaceRankingController;
import be.uhasselt.databasesproject.controller.user.MoreInfoRaceController;
import be.uhasselt.databasesproject.controller.user.RunnerUserController;
import be.uhasselt.databasesproject.controller.user.UserLoginController;
import be.uhasselt.databasesproject.controller.user.VolunteerUserController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.VolunteerJdbi;
import be.uhasselt.databasesproject.model.Race;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SwitchAnchorPane {

    public static AnchorPane anchorPane;
    private static final int defaultWidth = 585;
    private static final int defaultHeight = 435;

    private static void goTo(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();
            SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }
    }

    public static void goToMainMenu() {
        String resource = "/fxml/main.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Main Menu");
    }

    public static void goToAdmin() {
        String resource = "/fxml/admin/admin.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Admin");
    }

    public static void goToLoginAdmin() {
        String resource = "/fxml/admin/loginAdmin.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();
            setAdminLoginRunnerScreen(anchorPane, loader);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }
    }

    private static void setAdminLoginRunnerScreen(AnchorPane anchorPane, FXMLLoader loader) {
        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);

        LoginAdminController loginAdminController = loader.getController();
        loginAdminController.setStage(stage);

        stage.setScene(scene);
        stage.setTitle("login");
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public static void goToRunner() {
        String resource = "/fxml/admin/table/runner.fxml";
        goTo(resource);
        setStageSize(1170, defaultHeight);
        Main.getRootStage().setTitle("Runner");
    }

    public static void goToVolunteer() {
        String resource = "/fxml/admin/table/volunteer.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Volunteer");
    }

    public static void goToRegisterVolunteer() {
        String resource = "/fxml/user/registerVolunteer.fxml";
        goTo(resource);
        setStageSize(540, 320);
        Main.getRootStage().setTitle("Register");
    }

    public static void goToRunnerRace() {
        String resource = "/fxml/admin/table/runnerRace.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Runner race");
    }

    public static void goToRace() {
        String resource = "/fxml/admin/table/race.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Race");
    }

    public static void goToRegisterRunner() {
        String resource = "/fxml/user/registerRunner.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Register");
    }

    public static void goToSegment() {
        String resource = "/fxml/admin/table/segment.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Segment");
    }

    public static void goToSegmentTime() {
        String resource = "/fxml/admin/table/segmentTime.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Segment time");
    }

    public static void goToVolunteerRace() {
        String resource = "/fxml/admin/table/volunteerRace.fxml";
        goTo(resource);
        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Volunteer race");
    }

    public static void goToRanking(boolean isGlobalRanking, Race race) {
        String resource = "/fxml/ranking/raceRanking.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();

            RaceRankingController raceRankingController = loader.getController();
            if (isGlobalRanking) {
                raceRankingController.setGlobalRanking();
            } else {
                raceRankingController.setRaceRanking();
                raceRankingController.setRace(race);
            }

            raceRankingController.load();
            SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }

        setStageSize(defaultWidth, defaultHeight);
        Main.getRootStage().setTitle("Global ranking");
    }

    public static void goToRaceRankingMenu() {
        String resource = "/fxml/ranking/raceRankingMenu.fxml";
        goTo(resource);
        setStageSize(475, 345);
        Main.getRootStage().setTitle("Ranking menu");
    }

    public static void goToRankingMenu() {
        String resource = "/fxml/ranking/rankingMenu.fxml";
        goTo(resource);
        setStageSize(265, 205);
        Main.getRootStage().setTitle("Race ranking menu");
    }

    public static void goToLogin(boolean isRunner) {
        String resource = "/fxml/user/userLogin.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();
            setLoginRunnerScreen(anchorPane, loader, isRunner);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }
    }

    private static void setLoginRunnerScreen(AnchorPane anchorPane, FXMLLoader loader, boolean isRunner) {
        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);

        UserLoginController userLoginController = loader.getController();
        userLoginController.setStage(stage);

        if (isRunner) {
            userLoginController.setRunner();
        } else {
            userLoginController.setVolunteer();
        }

        stage.setScene(scene);
        stage.setTitle("login");
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public static void goToEditVolunteer(int id) {
        String resourceName = "/fxml/admin/edit/editVolunteer.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setEditVolunteerScreen(anchorPane, loader, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setEditVolunteerScreen(AnchorPane anchorPane, FXMLLoader loader, int id) {
        EditVolunteerController editVolunteerController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        editVolunteerController.inflateUI(volunteerJdbi.getById(id));
        editVolunteerController.setUserMode();

        setStageSize(750, 340);
        Main.getRootStage().setTitle("Edit volunteer");
    }

    public static void goToEditRunner(int id) {
        String resourceName = "/fxml/admin/edit/editRunner.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setEditRunnerScreen(anchorPane, loader, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setEditRunnerScreen(AnchorPane anchorPane, FXMLLoader loader, int id) {
        EditRunnerController editRunnerController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        editRunnerController.inflateUI(runnerJdbi.getById(id));
        editRunnerController.setUserMode();

        setStageSize(750, 340);
        Main.getRootStage().setTitle("Edit runner");
    }

    public static void goToRunnerUser(int id) {
        String resourceName = "/fxml/user/runnerUser.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setRunnerUserScreen(anchorPane, loader, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setRunnerUserScreen(AnchorPane anchorPane, FXMLLoader loader, int id) {
        RunnerUserController runnerUserController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        runnerUserController.inflateUI(runnerJdbi.getById(id));

        setStageSize(450, defaultHeight);
        Main.getRootStage().setTitle("runner user");
    }

    public static void goToVolunteerUser(int id) {
        String resourceName = "/fxml/user/volunteerUser.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setVolunteerUserScreen(anchorPane, loader, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setVolunteerUserScreen(AnchorPane anchorPane, FXMLLoader loader, int id) {
        VolunteerUserController volunteerUserController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        volunteerUserController.inflateUI(volunteerJdbi.getById(id));

        setStageSize(335, defaultHeight);
        Main.getRootStage().setTitle("volunteer user");
    }

    public static void goToMoreInfoRace(int raceId, int runnerId) {
        String resourceName = "/fxml/user/moreInfoRace.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setMoreInfoRaceScreen(anchorPane, loader, raceId, runnerId);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setMoreInfoRaceScreen(AnchorPane anchorPane, FXMLLoader loader, int raceId, int runnerId) {
        MoreInfoRaceController moreInfoRaceController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        moreInfoRaceController.inflateUI(raceJdbi.getById(raceId), runnerJdbi.getById(runnerId));

        setStageSize(750, defaultHeight);
        Main.getRootStage().setTitle("More info race");
    }

    private static void setStageSize(double width, double height) {
        Main.getRootStage().setWidth(width);
        Main.getRootStage().setHeight(height);
    }

    public static void setAnchorPane(AnchorPane anchorPane) {
        SwitchAnchorPane.anchorPane = anchorPane;
    }
}
