package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Segment;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class SegmentJdbi implements JdbiInterface<Segment> {

    private final Jdbi jdbi;

    public SegmentJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Segment> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM segment")
                .mapToBean(Segment.class)
                .list());
    }

    public List<Segment> getAllByRaceId(int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM segment WHERE raceID = :raceId")
                .bind("raceId", raceId)
                .mapToBean(Segment.class)
                .list());
    }

    @Override
    public void insert(Segment segment) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO segment (raceID, location, distance) VALUES (:raceId, :location, :distance)")
                .bindBean(segment)
                .execute());
    }

    @Override
    public void update(Segment segment) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE segment SET raceID = :raceId, location = :location, distance = :distance WHERE id = :id")
                .bindBean(segment)
                .execute());
    }

    public void updateRaceId(Segment segment, int raceId) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE segment SET raceID = :raceId WHERE id = :id")
                .bindBean(segment)
                .bind("raceId", raceId)
                .execute());
    }

    @Override
    public void delete(Segment segment) {
        jdbi.withHandle(handle -> {
            handle.createUpdate("DELETE FROM segment WHERE id = :id").bindBean(segment).execute();
            return handle.createUpdate("DELETE FROM segment_times WHERE segmentID = :id").bindBean(segment).execute();
        });
    }
}
