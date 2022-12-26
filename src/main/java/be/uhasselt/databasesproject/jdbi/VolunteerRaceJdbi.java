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
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(VolunteerRace volunteerRace) {
        //TODO
    }

    @Override
    public void update(VolunteerRace volunteerRace) {
        //TODO
    }

    @Override
    public void delete(VolunteerRace volunteerRace) {
        //TODO
    }
}
