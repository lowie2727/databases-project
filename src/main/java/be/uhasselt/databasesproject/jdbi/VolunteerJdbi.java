package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Volunteer;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class VolunteerJdbi {

    private final Jdbi jdbi;

    public VolunteerJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Volunteer> getVolunteers() {
        //TODO
        return Collections.emptyList();
    }

    public void insertVolunteer(Volunteer volunteer) {
        //TODO
    }

    public void updateVolunteer(Volunteer volunteer) {
        //TODO
    }

    public void deleteVolunteer(Volunteer volunteer) {
        //TODO
    }
}
