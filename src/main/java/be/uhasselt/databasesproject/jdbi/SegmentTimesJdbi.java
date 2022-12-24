package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.SegmentTimes;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class SegmentTimesJdbi {

    private final Jdbi jdbi;

    public SegmentTimesJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<SegmentTimes> getSegmentTimes() {
        //TODO
        return Collections.emptyList();
    }

    public void insertSegmentTimes(SegmentTimes segmentTimes) {
        //TODO
    }

    public void updateSegmentTimes(SegmentTimes segmentTimes) {
        //TODO
    }

    public void deleteSegmentTimes(SegmentTimes segmentTimes) {
        //TODO
    }
}
