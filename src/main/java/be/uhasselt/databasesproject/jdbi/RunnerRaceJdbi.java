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
        String query = "SELECT * FROM runner_race";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(RunnerRace.class)
                .list());
    }

    public void insert(RunnerRace runnerRace) {
        String query = "INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) " +
                "VALUES (:runnerId, :raceId, :shirtNumber, :time)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void update(RunnerRace runnerRace) {
        String query = "UPDATE runner_race SET shirtNumber = :shirtNumber, time = :time " +
                "WHERE runnerID = :runnerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void delete(RunnerRace runnerRace) {
        String query = "DELETE FROM runner_race " +
                "WHERE runnerID = :runnerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(runnerRace)
                .execute());
    }

    public void delete(int runnerId, int raceId) {
        String query = "DELETE FROM runner_race " +
                "WHERE runnerID = :runnerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .execute());
    }

    public void insert(int raceId) {
        int nextShirtNumber = getNextShirtNumber(raceId);
        String query = "INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) " +
                "VALUES (:runnerID, :raceID, :shirtNumber, :time)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bind("runnerID", getIdLatestAddedRunner())
                .bind("raceID", raceId)
                .bind("shirtNumber", nextShirtNumber)
                .bind("time", 0)
                .execute());
    }

    public int getIdLatestAddedRunner() {
        String query = "SELECT seq FROM SQLITE_SEQUENCE " +
                "WHERE name='runner'";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapTo(Integer.class)
                .one());
    }

    public int getNextShirtNumber(int raceId) {
        String query = "SELECT MAX(shirtNumber) FROM runner_race " +
                "WHERE raceID = :raceID";

        Integer shirtNumber = jdbi.withHandle(handle -> handle.createQuery(query)
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
        String query = "SELECT race.id, race.date, race.name, race.distance, race.price FROM runner_race " +
                "INNER JOIN race ON runner_race.raceID = race.id " +
                "WHERE runner_race.runnerID = :runnerId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("runnerId", runnerId)
                .mapToBean(Race.class)
                .list());
    }

    public RunnerRace getRunnerRaceById(int runnerId, int raceId) {
        String query = "SELECT * FROM runner_race " +
                "WHERE runnerID = :runnerId AND raceID = :raceId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .mapToBean(RunnerRace.class)
                .one());
    }
}
