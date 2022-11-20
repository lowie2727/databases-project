package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Runner;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class RunnerJdbi {

    private final Jdbi jdbi;

    public RunnerJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Runner> getRunnerByFirstName(String firstName) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner WHERE firstName = :firstName")
                .bind("firstName", firstName)
                .mapToBean(Runner.class)
                .list());
    }

    public List<Runner> getRunners() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM runner")
                .mapToBean(Runner.class)
                .list());
    }

    public void insertRunner(Runner runner) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner (id, firstName, familyName, age, weight, length, streetName, houseNumber, boxNumber, postalCode, city, country) VALUES (:id, :firstName, :familyName, :age, :weight, :length, :streetName, :houseNumber, :boxNumber, :postalCode, :city, :country)")
                .bindBean(runner)
                .execute());
    }
}
