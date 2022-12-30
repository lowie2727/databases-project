package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.VolunteerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
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
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO volunteer_race(volunteerID, raceID) VALUES (:volunteerId, :raceId)")
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void update(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE volunteerRace SET volunteerID = :volunteerId, raceID = :raceId")
                .bindBean(volunteerRace)
                .execute());
    }

    @Override
    public void delete(VolunteerRace volunteerRace) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM volunteerRace").bindBean(volunteerRace).execute();
        });
    }
}
