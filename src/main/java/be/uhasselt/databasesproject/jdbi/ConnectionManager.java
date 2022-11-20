package be.uhasselt.databasesproject.jdbi;

import org.jdbi.v3.core.Jdbi;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConnectionManager {
    public static final String ConnectionString = "jdbc:sqlite:src/main/resources/be/uhasselt/databasesproject/project.sqlite3";

    public static void initTables() {
        Jdbi jdbi = Jdbi.create(ConnectionString);
        try {
            var uri = Objects.requireNonNull(ConnectionManager.class.getResource("/be/uhasselt/databasesproject/dbcreate.sql")).toURI();
            var sql = new String(Files.readAllBytes(Paths.get(uri)));

            jdbi.useHandle(handle -> handle.createScript(sql).execute());
        } catch (Exception e) {
            System.out.println("kan bestand niet inladen");
        }
    }
}
