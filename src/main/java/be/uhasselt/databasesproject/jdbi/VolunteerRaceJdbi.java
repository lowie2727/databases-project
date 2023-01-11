package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.RunnerRace;
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
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO volunteer_race (volunteerID, raceID) VALUES (:volunteerId, :raceId)")
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void update(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE volunteer_race SET volunteerID = :volunteerId, raceID = :raceId")
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
