package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.GlobalRanking;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalRankingJdbi implements JdbiInterface<GlobalRanking> {

    private final Jdbi jdbi;

    public GlobalRankingJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<GlobalRanking> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM global_ranking ")
                .mapToBean(GlobalRanking.class)
                .list());
    }

    @Override
    public void insert(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO global_ranking (prizeMoney, averageSpeed) VALUES (:prizeMoney, :averageSpeed)")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void update(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE global_ranking SET prizeMoney = :prizeMoney, averageSpeed = :averageSpeed WHERE runnerID = :runnerId")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void delete(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM global_ranking WHERE runnerID = :runnerId")
                .bindBean(globalRanking)
                .execute());
    }

    public List<GlobalRanking> getAllRanked() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM global_ranking ORDER BY averageSpeed DESC")
                .mapToBean(GlobalRanking.class)
                .list());
    }

    private int getTotalTimeRunner(int runnerId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT SUM(runner_race.time) from runner INNER join runner_race ON id = runner_race.runnerID WHERE runner.id = :runnerId")
                .bind("runnerId", runnerId)
                .mapTo(Integer.class)
                .one());
    }

    private int getTotalDistanceRunner(int runnerId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT SUM(race.distance) from race INNER join runner_race ON race.id = runner_race.raceID INNER join runner ON runner_race.runnerID = runner.id WHERE runner.id = :runnerId")
                .bind("runnerId", runnerId)
                .mapTo(Integer.class)
                .one());
    }

    public void setAverageSpeedRunner(int runnerId) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE global_ranking SET averageSpeed = :averageSpeed WHERE runnerID = :runnerId")
                .bind("averageSpeed", calculateAverageSpeed(runnerId))
                .bind("runnerId", runnerId)
                .execute());
    }

    private double calculateAverageSpeed(int runnerId) {
        double distance;
        double time;

        distance = getTotalDistanceRunner(runnerId);
        time = getTotalTimeRunner(runnerId);

        if (time == 0) {
            time = 1;
            distance = 0;
        }

        double averageSpeed = (distance / time) * 3.6;
        averageSpeed = Math.round(averageSpeed * 10);

        return averageSpeed / 10;
    }

    public void calculatePrizeMoney() {
        List<Integer> prizeMoney = new ArrayList<>();
        Collections.addAll(prizeMoney, 100, 50, 20, 10, 5);
        int index = 0;
        int size = prizeMoney.size();

        List<GlobalRanking> globalRanking = getAllRanked();
        for (GlobalRanking g : globalRanking) {
            if (index < size) {
                g.setPrizeMoney(prizeMoney.get(index));
                update(g);
                index++;
            }
        }
    }
}
