package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.RunnerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class RunnerRaceJdbi implements JdbiInterface<RunnerRace> {

    private final Jdbi jdbi;

    public RunnerRaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<RunnerRace> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner_race")
                .mapToBean(RunnerRace.class)
                .list());
    }

    @Override
    public void insert(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) VALUES (:runnerId, :raceId, :shirtNumber, :time")
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void update(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE runner_race SET runnerID = :runnerId, raceID = :raceId, shirtNumber = :shirtNumber, time = :time")
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void delete(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM runner_race WHERE runnerID = :runnerId AND raceID = :raceId")
                .bindBean(runnerRace)
                .execute());
    }

    public void insert(int raceId) {
        int nextShirtNumber = getNextShirtNumber(raceId);

        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) VALUES (:runnerID, :raceID, :shirtNumber, :time)")
                .bind("runnerID", getIdLatestAddedRunner())
                .bind("raceID", raceId)
                .bind("shirtNumber", nextShirtNumber)
                .bind("time", 0)
                .execute());
    }

    public int getIdLatestAddedRunner() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE name='runner'")
                .mapTo(Integer.class)
                .one());
    }

    public int getNextShirtNumber(int raceId) {
        Integer shirtNumber = jdbi.withHandle(handle -> handle.createQuery("SELECT MAX(shirtNumber) FROM runner_race WHERE raceID = :raceID")
                .bind("raceID", raceId)
                .mapTo(Integer.class).one());

        if (shirtNumber == null) {
            shirtNumber = 1;
        } else {
            shirtNumber++;
        }
        return shirtNumber;
    }
}
