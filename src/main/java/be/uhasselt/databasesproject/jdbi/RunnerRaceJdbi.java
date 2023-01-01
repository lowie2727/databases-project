package be.uhasselt.databasesproject.jdbi;

import org.jdbi.v3.core.Jdbi;

public class RunnerRaceJdbi {

    private final Jdbi jdbi;

    public RunnerRaceJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public void insert(int raceId) {
        int nextShirtNumber = getNextShirtNumber(raceId);

        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO runner_race (runnerID, raceID, shirtNumber, time) VALUES (:runnerID, :raceID, :shirtNumber, :time)")
                .bind("runnerID", getIdLatestAddedRunner())
                .bind("raceID", raceId)
                .bind("shirtNumber", nextShirtNumber)
                .bind("time", 0)
                .execute());
    }

    public int getIdLatestAddedRunner() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT seq FROM SQLITE_SEQUENCE WHERE name='runner'").mapTo(Integer.class).one());
    }

    public int getNextShirtNumber(int raceId) {
        Integer shirtNumber = jdbi.withHandle(handle -> handle.createQuery("SELECT MAX(shirtNumber) FROM runner_race WHERE raceID = :raceID")
                .bind("raceID", raceId)
                .mapTo(Integer.class).one());

        if (shirtNumber == null) {
            shirtNumber = 1;
        } else {
            shirtNumber++;
        }
        return shirtNumber;
    }
}
