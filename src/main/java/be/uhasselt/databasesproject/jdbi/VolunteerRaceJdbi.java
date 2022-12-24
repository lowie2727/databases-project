package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.VolunteerRace;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class VolunteerRaceJdbi {

    private final Jdbi jdbi;

    public VolunteerRaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<VolunteerRace> getVolunteerRaces() {
        //TODO
        return Collections.emptyList();
    }

    public void insertVolunteerRace(VolunteerRace volunteerRace) {
        //TODO
    }

    public void updateVolunteerRace(VolunteerRace volunteerRace) {
        //TODO
    }

    public void deleteVolunteerRace(VolunteerRace volunteerRace) {
        //TODO
    }
}
