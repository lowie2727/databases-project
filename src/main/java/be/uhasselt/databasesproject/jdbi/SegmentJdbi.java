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
        String query = "SELECT * FROM segment";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(Segment.class)
                .list());
    }

    public List<Segment> getAllByRaceId(int raceId) {
        String query = "SELECT * FROM segment " +
                "WHERE raceID = :raceId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("raceId", raceId)
                .mapToBean(Segment.class)
                .list());
    }

    @Override
    public void insert(Segment segment) {
        String query = "INSERT INTO segment (raceID, location, distance) " +
                "VALUES (:raceId, :location, :distance)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(segment)
                .execute());
    }

    @Override
    public void update(Segment segment) {
        String query = "UPDATE segment SET raceID = :raceId, location = :location, distance = :distance " +
                "WHERE id = :id";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(segment)
                .execute());
    }

    public void updateRaceId(Segment segment, int raceId) {
        String query = "UPDATE segment SET raceID = :raceId " +
                "WHERE id = :id";

        jdbi.withHandle(handle -> handle.createUpdate(query)
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

    public void deleteByRaceId(int raceId) {
        String query = "DELETE FROM segment " +
                "WHERE raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bind("raceId", raceId)
                .execute());
    }
}
