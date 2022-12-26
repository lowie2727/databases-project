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
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(RunnerRace runnerRace) {
        //TODO
    }

    @Override
    public void update(RunnerRace runnerRace) {
        //TODO
    }

    @Override
    public void delete(RunnerRace runnerRace) {
        //TODO
    }
}
