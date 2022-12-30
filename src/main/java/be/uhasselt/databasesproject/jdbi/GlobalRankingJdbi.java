package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.GlobalRanking;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
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
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO global_ranking(runnerID, prizeMoney, totalTime) VALUES (:runnerId, :prizeMoney, :totalTime)")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void update(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE globalRanking SET runnerID = :runnerId, prizeMoney = :prizeMoney, totalTime = :totalTime")
                .bindBean(globalRanking)
                .execute());
    }

    @Override
    public void delete(GlobalRanking globalRanking) {
        jdbi.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM globalRanking").bindBean(globalRanking).execute();
        });
    }
}
