package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.SegmentTimes;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class SegmentTimesJdbi implements JdbiInterface<SegmentTimes> {

    private final Jdbi jdbi;

    public SegmentTimesJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<SegmentTimes> getAll() {
        String query = "SELECT * FROM segment_times";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(SegmentTimes.class)
                .list());
    }

    @Override
    public void insert(SegmentTimes segmentTimes) {
        String query = "INSERT INTO segment_times (runnerID, segmentID, time) " +
                "VALUES (:runnerId, :segmentId, :time)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(segmentTimes)
                .execute());
    }

    @Override
    public void update(SegmentTimes segmentTimes) {
        String query = "UPDATE segment_times SET time = :time " +
                "WHERE runnerID = :runnerId AND segmentID = :segmentId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(segmentTimes)
                .execute());
    }

    @Override
    public void delete(SegmentTimes segmentTimes) {
        String query = "DELETE FROM segment_times WHERE segmentID = :segmentId AND runnerID = :runnerId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(segmentTimes)
                .execute());
    }
}
