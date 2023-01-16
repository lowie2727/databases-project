package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.RunnerRanking;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;

import java.util.List;

public class RankingJdbi {

    private final Jdbi jdbi;

    public RankingJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
        jdbi.getConfig(SqlStatements.class).setUnusedBindingAllowed(true);
    }


    public List<RunnerRanking> getTimesFromRace(int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT runner.firstName, runner.familyName, runner_race.time FROM race INNER JOIN runner_race ON runner_race.raceID = race.id INNER JOIN runner ON runner_race.runnerID = runner.id WHERE race.id = :raceId ORDER BY time DESC")
                .bind("raceId", raceId)
                .map((rs, ctx) -> new RunnerRanking(rs.getString("firstName"), rs.getString("familyName"), rs.getInt("time"), 0))
                .list());
    }
}
