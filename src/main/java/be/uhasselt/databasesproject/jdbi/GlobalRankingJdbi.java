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
        //TODO
        return Collections.emptyList();
    }

    @Override
    public void insert(GlobalRanking globalRanking) {
        //TODO
    }

    @Override
    public void update(GlobalRanking globalRanking) {
        //TODO
    }

    @Override
    public void delete(GlobalRanking globalRanking) {
        //TODO
    }
}
