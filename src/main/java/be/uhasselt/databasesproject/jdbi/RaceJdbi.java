package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Race;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class RaceJdbi {

    private final Jdbi jdbi;

    public RaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Race> getRaces() {
        //TODO
        return Collections.emptyList();
    }

    public void insertRace(Race race) {
        //TODO
    }

    public void updateRace(Race race) {
        //TODO
    }

    public void deleteRace(Race race) {
        //TODO
    }
}
