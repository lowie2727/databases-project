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
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM volunteer_race")
                .mapToBean(VolunteerRace.class)
                .list());
    }

    @Override
    public void insert(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO volunteer_race (volunteerID, raceID, job) VALUES (:volunteerId, :raceId, :job)")
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void update(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE volunteer_race SET job = :job WHERE volunteerID = :volunteerId AND raceID = :raceId")
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void delete(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM volunteer_race WHERE volunteerID = :volunteerId AND raceID = :raceId")
                .bindBean(volunteerRace)
                .execute());
    }

    public void delete(int volunteerId, int raceId) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM volunteer_race WHERE volunteerID = :volunteerId AND raceID = :raceId")
                .bind("volunteerId", volunteerId)
                .bind("raceId", raceId)
                .execute());
    }

    public void insert(int raceId) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO volunteer_race (volunteerID, raceID, job) VALUES (:volunteerID, :raceID, :job)")
                .bind("volunteerID", getIdLatestAddedVolunteer())
                .bind("raceID", raceId)
                .bind("job", "nog niet toegewezen")
                .execute());
    }

    public int getIdLatestAddedVolunteer() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE name='volunteer'")
                .mapTo(Integer.class)
                .one());
    }

    public List<Race> getRegisteredRaces(int volunteerId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT race.id, race.date, race.name, race.distance, race.price FROM volunteer_race INNER JOIN race ON volunteer_race.raceID = race.id WHERE volunteer_race.volunteerID = :volunteerId")
                .bind("volunteerId", volunteerId)
                .mapToBean(Race.class)
                .list());
    }

    public VolunteerRace getVolunteerRaceById(int volunteerId, int raceId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM volunteer_race WHERE volunteerID = :volunteerId AND raceID = :raceId")
                .bind("volunteerId", volunteerId)
                .bind("raceId", raceId)
                .mapToBean(VolunteerRace.class)
                .one());
    }


}
