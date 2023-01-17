package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Runner;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class RunnerJdbi implements JdbiInterface<Runner> {

    private final Jdbi jdbi;

    public RunnerJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Runner> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner")
                .mapToBean(Runner.class)
                .list());
    }

    @Override
    public void insert(Runner runner) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner (firstName, familyName, age, weight, length, password, username, streetName, houseNumber, boxNumber, postalCode, city, country) VALUES (:firstName, :familyName, :age, :weight, :length, :password, :username, :streetName, :houseNumber, :boxNumber, :postalCode, :city, :country)")
                .bindBean(runner)
                .execute());
    }

    @Override
    public void update(Runner runner) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE runner SET firstName = :firstName, familyName = :familyName, age = :age, weight = :weight, length = :length, password = :password, username = :username, streetName = :streetName, houseNumber = :houseNumber, boxNumber = :boxNumber, postalCode = :postalCode, city = :city, country = :country WHERE id = :id")
                .bindBean(runner)
                .execute());
    }

    @Override
    public void delete(Runner runner) {
        jdbi.withHandle(handle -> {
            handle.createUpdate("DELETE FROM runner WHERE id = :id").bindBean(runner).execute();
            handle.createUpdate("DELETE FROM global_ranking WHERE runnerID = :id").bindBean(runner).execute();
            handle.createUpdate("DELETE FROM runner_race WHERE runnerID = :id").bindBean(runner).execute();
            return handle.createUpdate("DELETE FROM segment_times WHERE runnerID = :id").bindBean(runner).execute();
        });
    }

    public Runner getById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner WHERE id = :id")
                .bind("id", id)
                .mapToBean(Runner.class)
                .one());
    }

    public Runner getByUsername(String username) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner WHERE username = :username")
                .bind("username", username)
                .mapToBean(Runner.class)
                .one());
    }

    public List<String> getAllUsernames() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT username FROM runner")
                .mapTo(String.class)
                .list());
    }


    public String getHashedPassword(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT password FROM runner WHERE id = :id")
                .bind("id", id)
                .mapTo(String.class)
                .one());
    }

    private int getIdLatestAddedRunner() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE name='runner'")
                .mapTo(Integer.class)
                .one());
    }

    public void insertIntoSegmentTimes(int raceId) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO segment_times (runnerID, segmentID, time) SELECT :runnerId, segment.id, 0 FROM segment WHERE raceID = :raceId")
                .bind("runnerId", getIdLatestAddedRunner())
                .bind("raceId", raceId)
                .execute());
    }

    public void insertIntoSegmentTimes(int raceId, int runnerId) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO segment_times (runnerID, segmentID, time) SELECT :runnerId, segment.id, 0 FROM segment WHERE raceID = :raceId")
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .execute());
    }

    public void deleteFromSegment(int raceId, int runnerId) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM segment_times WHERE segment_times.segmentID IN (SELECT segment.id FROM segment INNER JOIN race ON segment.raceID = race.id INNER JOIN segment_times ON segment.id = segment_times.segmentID WHERE race.id = :raceId AND segment_times.runnerID = :runnerId)")
                .bind("runnerId", runnerId)
                .bind("raceId", raceId)
                .execute());
    }

    public void insertGlobal(Runner runner, int raceId) {
        insert(runner);

        if (raceId != -1) {
            insertIntoSegmentTimes(raceId);
        }
    }
}
