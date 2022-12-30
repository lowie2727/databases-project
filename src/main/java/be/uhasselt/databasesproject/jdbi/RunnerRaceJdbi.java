package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.RunnerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
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
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner_race(runnerID, raceID, shirtNumber, time) VALUES (:runnerId, :raceId, :shirtNumber, :time")
                .bindBean(runnerRace)
                .execute());
    }

    @Override
    public void update(RunnerRace runnerRace) {

        jdbi.withHandle(handle -> handle.createUpdate("UPDATE runnerRace SET runnerID = :runnerId, raceID = :raceId, shirtNumber = :shirtNumber, time = :time")
                .bindBean(runnerRace)
                .execute());

    }

    @Override
    public void delete(RunnerRace runnerRace) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM runnerRace").bindBean(runnerRace).execute();
        });
    }
}
