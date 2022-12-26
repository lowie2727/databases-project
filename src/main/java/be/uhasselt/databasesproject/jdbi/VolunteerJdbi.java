package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Volunteer;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class VolunteerJdbi implements JdbiInterface<Volunteer> {

    private final Jdbi jdbi;

    public VolunteerJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Volunteer> getAll() {
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(Volunteer volunteer) {
        //TODO
    }

    @Override
    public void update(Volunteer volunteer) {
        //TODO
    }

    @Override
    public void delete(Volunteer volunteer) {
        //TODO
    }
}
