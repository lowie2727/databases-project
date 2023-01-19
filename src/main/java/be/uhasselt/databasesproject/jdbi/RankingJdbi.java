package be.uhasselt.databasesproject.jdbi;

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

    public List<RunnerRanking> getGlobalRanking() {
        String query = "SELECT runner.firstName, runner.familyName, SUM(runner_race.time) AS totalTime, (CAST(SUM(race.distance) AS REAL)/SUM(runner_race.time)) * 3.6 AS averageSpeed FROM runner " +
                "INNER JOIN runner_race ON runner.id = runner_race.runnerID " +
                "INNER JOIN race ON runner_race.raceID = race.id " +
                "WHERE runner_race.time > 0 " +
                "GROUP BY runner.id " +
                "ORDER BY averageSpeed DESC";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .map((rs, ctx) -> new RunnerRanking(rs.getString("firstName"), rs.getString("familyName"), rs.getInt("totalTime"), rs.getDouble("averageSpeed")))
                .list());
    }

    public List<RunnerRanking> getRaceRanking(int raceId) {
        String query = "SELECT runner.firstName, runner.familyName, SUM(runner_race.time) AS totalTime, (CAST(SUM(race.distance) AS REAL)/SUM(runner_race.time)) * 3.6 AS averageSpeed FROM runner " +
                "INNER JOIN runner_race ON runner.id = runner_race.runnerID " +
                "INNER JOIN race ON runner_race.raceID = race.id " +
                "WHERE runner_race.time > 0 AND race.id = :raceId " +
                "GROUP BY runner.id " +
                "ORDER BY averageSpeed DESC";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("raceId", raceId)
                .map((rs, ctx) -> new RunnerRanking(rs.getString("firstName"), rs.getString("familyName"), rs.getInt("totalTime"), rs.getDouble("averageSpeed")))
                .list());
    }
}
