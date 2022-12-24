package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.GlobalRanking;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class GlobalRankingJdbi {

    private final Jdbi jdbi;

    public GlobalRankingJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<GlobalRanking> getGlobalRanking() {
        //TODO
        return Collections.emptyList();
    }

    public void insertGlobalRanking(GlobalRanking globalRanking) {
        //TODO
    }

    public void updateGlobalRanking(GlobalRanking globalRanking) {
        //TODO
    }

    public void deleteGlobalRanking(GlobalRanking globalRanking) {
        //TODO
    }
}
