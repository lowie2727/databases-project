package be.uhasselt.databasesproject.jdbi;

import org.jdbi.v3.core.Jdbi;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConnectionManager {
    public static final String ConnectionString = "jdbc:sqlite:src/main/resources/database/project.sqlite3";

    public static void initTables() {
        Jdbi jdbi = Jdbi.create(ConnectionString);
        try {
            URI uri = Objects.requireNonNull(ConnectionManager.class.getResource("/database/dbcreate.sql")).toURI();
            String sql = new String(Files.readAllBytes(Paths.get(uri)));

            jdbi.useHandle(handle -> handle.createScript(sql).execute());
            System.out.println("file loaded\n");
        } catch (Exception e) {
            System.out.println("cannot load file");
        }
    }
}
