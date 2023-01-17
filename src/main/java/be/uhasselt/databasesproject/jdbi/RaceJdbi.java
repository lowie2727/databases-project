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
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM race")
                .mapToBean(Race.class)
                .list());
    }

    @Override
    public void insert(Race race) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO race (date, name, distance, price) VALUES (:date, :name, :distance, :price)")
                .bindBean(race)
                .execute());
    }

    @Override
    public void update(Race race) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE race SET date = :date, name = :name, distance = :distance, price = :price WHERE id = :id")
                .bindBean(race)
                .execute());
    }

    @Override
    public void delete(Race race) {
        jdbi.withHandle(handle -> {
            handle.createUpdate("DELETE FROM race WHERE id = :id").bindBean(race).execute();
            handle.createUpdate("DELETE FROM volunteer_race WHERE raceID = :id").bindBean(race).execute();
            handle.createUpdate("DELETE FROM runner_race WHERE raceID = :id").bindBean(race).execute();
            return handle.createUpdate("DELETE FROM segment WHERE raceID = :id").bindBean(race).execute();
        });
    }

    public List<Race> getAllUpcoming() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM race WHERE date > date('now', 'localtime')")
                .mapToBean(Race.class)
                .list());
    }

    public List<Race> getAllPrevious() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM race WHERE date < date('now', 'localtime')")
                .mapToBean(Race.class)
                .list());
    }

    public int getIdLatestAddedRace() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE name = 'race'")
                .mapTo(Integer.class)
                .one());
    }

    public Race getById(int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM race WHERE id = :raceId")
                .bind("raceId", raceId)
                .mapToBean(Race.class)
                .one());
    }
}
