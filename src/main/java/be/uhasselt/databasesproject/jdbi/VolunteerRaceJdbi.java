package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.VolunteerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class VolunteerRaceJdbi implements JdbiInterface<VolunteerRace> {

    private final Jdbi jdbi;

    public VolunteerRaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<VolunteerRace> getAll() {
        String query = "SELECT * FROM volunteer_race";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(VolunteerRace.class)
                .list());
    }

    @Override
    public void insert(VolunteerRace volunteerRace) {
        String query = "INSERT INTO volunteer_race (volunteerID, raceID, job) " +
                "VALUES (:volunteerId, :raceId, :job)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void update(VolunteerRace volunteerRace) {
        String query = "UPDATE volunteer_race SET job = :job " +
                "WHERE volunteerID = :volunteerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void delete(VolunteerRace volunteerRace) {
        String query = "DELETE FROM volunteer_race " +
                "WHERE volunteerID = :volunteerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteerRace)
                .execute());
    }

    public void delete(int volunteerId, int raceId) {
        String query = "DELETE FROM volunteer_race " +
                "WHERE volunteerID = :volunteerId AND raceID = :raceId";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bind("volunteerId", volunteerId)
                .bind("raceId", raceId)
                .execute());
    }

    public void insert(int raceId) {
        String query = "INSERT INTO volunteer_race (volunteerID, raceID, job) " +
                "VALUES (:volunteerID, :raceID, :job)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bind("volunteerID", getIdLatestAddedVolunteer())
                .bind("raceID", raceId)
                .bind("job", "nog niet toegewezen")
                .execute());
    }

    public int getIdLatestAddedVolunteer() {
        String query = "SELECT seq FROM SQLITE_SEQUENCE WHERE name='volunteer'";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapTo(Integer.class)
                .one());
    }

    public List<Race> getRegisteredRaces(int volunteerId) {
        String query = "SELECT race.id, race.date, race.name, race.distance, race.price FROM volunteer_race " +
                "INNER JOIN race ON volunteer_race.raceID = race.id " +
                "WHERE volunteer_race.volunteerID = :volunteerId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("volunteerId", volunteerId)
                .mapToBean(Race.class)
                .list());
    }

    public VolunteerRace getVolunteerRaceById(int volunteerId, int raceId) {
        String query = "SELECT * FROM volunteer_race " +
                "WHERE volunteerID = :volunteerId AND raceID = :raceId";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("volunteerId", volunteerId)
                .bind("raceId", raceId)
                .mapToBean(VolunteerRace.class)
                .one());
    }
}
