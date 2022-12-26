package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Segment;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class SegmentJdbi implements JdbiInterface<Segment> {

    private final Jdbi jdbi;

    public SegmentJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Segment> getAll() {
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(Segment segment) {
        //TODO
    }

    @Override
    public void update(Segment segment) {
        //TODO
    }

    @Override
    public void delete(Segment segment) {
        //TODO
    }
}
