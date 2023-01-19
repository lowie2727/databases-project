package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class RaceJdbi implements JdbiInterface<Race> {

    private final Jdbi jdbi;

    public RaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Race> getAll() {
        String query = "SELECT * FROM race";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(Race.class)
                .list());
    }

    @Override
    public void insert(Race race) {
        String query = "INSERT INTO race (date, name, distance, price) " +
                "VALUES (:date, :name, :distance, :price)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(race)
                .execute());
    }

    @Override
    public void update(Race race) {
        String query = "UPDATE race SET date = :date, name = :name, distance = :distance, price = :price " +
                "WHERE id = :id";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(race)
                .execute());
    }

    @Override
    public void delete(Race race) {
        jdbi.withHandle(handle -> {
            handle.createUpdate("DELETE FROM volunteer_race WHERE raceID = :id").bindBean(race).execute();
            handle.createUpdate("DELETE FROM runner_race WHERE raceID = :id").bindBean(race).execute();
            handle.createUpdate("DELETE FROM segment_times WHERE " +
                            "segment_times.segmentID IN (SELECT segment.id FROM segment " +
                            "WHERE segment.raceID = :id)")
                    .bindBean(race).execute();
            handle.createUpdate("DELETE FROM segment WHERE raceID = :id").bindBean(race).execute();
            return handle.createUpdate("DELETE FROM race WHERE id = :id").bindBean(race).execute();
        });
    }

    public List<Race> getAllUpcoming() {
        String query = "SELECT * FROM race " +
                "WHERE date > date('now', 'localtime')";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(Race.class)
                .list());
    }

    public List<Race> getAllPrevious() {
        String query = "SELECT * FROM race " +
                "WHERE date < date('now', 'localtime')";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(Race.class)
                .list());
    }

    public int getIdLatestAddedRace() {
        String query = "SELECT seq FROM SQLITE_SEQUENCE " +
                "WHERE name = 'race'";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapTo(Integer.class)
                .one());
    }

    public Race getById(int raceId) {
        String query = "SELECT * FROM race " +
                "WHERE id = :raceId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("raceId", raceId)
                .mapToBean(Race.class)
                .one());
    }
}
