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
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(SegmentTimes segmentTimes) {
        //TODO
    }

    @Override
    public void update(SegmentTimes segmentTimes) {
        //TODO
    }

    @Override
    public void delete(SegmentTimes segmentTimes) {
        //TODO
    }
}
