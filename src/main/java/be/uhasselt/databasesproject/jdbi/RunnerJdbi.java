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
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner (firstName, familyName, age, weight, length, streetName, houseNumber, boxNumber, postalCode, city, country) VALUES (:firstName, :familyName, :age, :weight, :length, :streetName, :houseNumber, :boxNumber, :postalCode, :city, :country)")
                .bindBean(runner)
                .execute());
    }

    @Override
    public void update(Runner runner) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE runner SET firstName = :firstName, familyName = :familyName, age = :age, weight = :weight, length = :length, streetName = :streetName, houseNumber = :houseNumber, boxNumber = :boxNumber, postalCode = :postalCode, city = :city, country = :country WHERE id = :id")
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
}
