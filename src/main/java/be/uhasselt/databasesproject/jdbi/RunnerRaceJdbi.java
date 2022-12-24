package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.RunnerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class RunnerRaceJdbi {

    private final Jdbi jdbi;

    public RunnerRaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<RunnerRace> getRunnerRaces() {
        //TODO
        return Collections.emptyList();
    }

    public void insertRunnerRace(RunnerRace runnerRace) {
        //TODO
    }

    public void updateRunnerRace(RunnerRace runnerRace) {
        //TODO
    }

    public void deleteRunnerRace(RunnerRace runnerRace) {
        //TODO
    }
}
