package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.GlobalRanking;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class GlobalRankingJdbi implements JdbiInterface<GlobalRanking> {

    private final Jdbi jdbi;

    public GlobalRankingJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<GlobalRanking> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM global_ranking")
                .mapToBean(GlobalRanking.class)
                .list());
    }

    @Override
    public void insert(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO global_ranking (prizeMoney, totalTime) VALUES (:prizeMoney, :totalTime)")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void update(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE global_ranking SET prizeMoney = :prizeMoney, totalTime = :totalTime WHERE runnerID = :runnerId")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void delete(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM global_ranking WHERE runnerID = :runnerId")
                .bindBean(globalRanking)
                .execute());
    }
}
