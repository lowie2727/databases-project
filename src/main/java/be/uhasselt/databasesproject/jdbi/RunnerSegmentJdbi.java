package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.RunnerSegment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;

import java.util.List;

public class RunnerSegmentJdbi {

    private final Jdbi jdbi;

    public RunnerSegmentJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
        jdbi.getConfig(SqlStatements.class).setUnusedBindingAllowed(true);
    }

    public List<RunnerSegment> getSegmentAndTimes(int raceId, int runnerId) {
        String query = "SELECT segment.location, segment.distance, segment_times.time AS totalTime, (CAST(segment.distance AS REAL)/segment_times.time) * 3.6 AS averageSpeed FROM segment " +
                "INNER JOIN segment_times ON segment.id = segment_times.segmentID " +
                "INNER JOIN race ON race.id = segment.raceID " +
                "INNER JOIN runner ON runner.id = segment_times.runnerID " +
                "WHERE race.id = :raceId AND runner.id = :runnerId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("raceId", raceId)
                .bind("runnerId", runnerId)
                .map((rs, ctx) -> new RunnerSegment(rs.getString("location"), rs.getInt("distance"), rs.getInt("totalTime"), rs.getDouble("averageSpeed")))
                .list());
    }
}
