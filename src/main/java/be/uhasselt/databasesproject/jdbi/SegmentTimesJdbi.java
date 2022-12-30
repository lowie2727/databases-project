package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.SegmentTimes;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class SegmentTimesJdbi implements JdbiInterface<SegmentTimes> {

    private final Jdbi jdbi;

    public SegmentTimesJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<SegmentTimes> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM segment_times")
                .mapToBean(SegmentTimes.class)
                .list());
    }

    @Override
    public void insert(SegmentTimes segmentTimes) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO segment_times(segmentID, runnerID, time) VALUES (:segmentId, :runnerId, :time)")
                .bindBean(segmentTimes)
                .execute());
    }

    @Override
    public void update(SegmentTimes segmentTimes) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE segmentTimes SET segmentID = :segmentId, runnerID = :runnerId, time = :time")
                .bindBean(segmentTimes)
                .execute());
    }

    @Override
    public void delete(SegmentTimes segmentTimes) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM segmentTimes").bindBean(segmentTimes).execute();
        });
    }
}
