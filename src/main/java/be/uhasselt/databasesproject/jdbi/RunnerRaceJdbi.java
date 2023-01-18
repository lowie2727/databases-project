package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
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

    public void insert(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) VALUES (:runnerId, :raceId, :shirtNumber, :time)")
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void update(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE runner_race SET shirtNumber = :shirtNumber, time = :time WHERE runnerID = :runnerId AND raceID = :raceId")
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void delete(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM runner_race WHERE runnerID = :runnerId AND raceID = :raceId")
                .bindBean(runnerRace)
                .execute());
    }

    public void delete(int runnerId, int raceId) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM runner_race WHERE runnerID = :runnerId AND raceID = :raceId")
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
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

    public List<Race> getRegisteredRaces(int runnerId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT race.id, race.date, race.name, race.distance, race.price FROM runner_race INNER JOIN race ON runner_race.raceID = race.id WHERE runner_race.runnerID = :runnerId")
                .bind("runnerId", runnerId)
                .mapToBean(Race.class)
                .list());
    }

    public RunnerRace getRunnerRaceById(int runnerId, int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner_race WHERE runnerID = :runnerId AND raceID = :raceId")
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .mapToBean(RunnerRace.class)
                .one());
    }

    public int getTime(int runnerId, int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT time FROM runner_race WHERE runnerID= :runnerId AND raceID = :raceId")
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .mapTo(Integer.class)
                .one());
    }
}
