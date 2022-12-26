package be.uhasselt.databasesproject.jdbi;

import java.util.List;

public interface JdbiInterface<T> {

    List<T> getAll();

    void insert(T value);

    void update(T value);

    void delete(T value);
}
