package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Segment;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class SegmentJdbi {

    private final Jdbi jdbi;

    public SegmentJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Segment> getSegments() {
        //TODO
        return Collections.emptyList();
    }

    public void insertSegment(Segment segment) {
        //TODO
    }

    public void updateSegment(Segment segment) {
        //TODO
    }

    public void deleteSegment(Segment segment) {
        //TODO
    }
}
