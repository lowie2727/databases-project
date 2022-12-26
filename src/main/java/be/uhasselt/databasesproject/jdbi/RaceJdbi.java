package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class RaceJdbi implements JdbiInterface<Race> {

    private final Jdbi jdbi;

    public RaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Race> getAll() {
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(Race race) {
        //TODO
    }

    @Override
    public void update(Race race) {
        //TODO
    }

    @Override
    public void delete(Race race) {
        //TODO
    }
}
