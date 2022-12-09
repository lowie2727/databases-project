package be.uhasselt.databasesproject.jdbi;

import org.jdbi.v3.core.Jdbi;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

public class ConnectionManager {
    public static final String CONNECTION_STRING = "jdbc:sqlite:src/main/resources/database/project.sqlite3";
    private static final Logger LOG = Logger.getLogger(ConnectionManager.class.getName());
    public static void initTables() {
        final Jdbi jdbi = Jdbi.create(CONNECTION_STRING);
        try {
            final URI uri = Objects.requireNonNull(ConnectionManager.class.getResource("/database/dbcreate.sql")).toURI();
            final String sql = new String(Files.readAllBytes(Paths.get(uri)));

            jdbi.useHandle(handle -> handle.createScript(sql).execute());
            LOG.info("file loaded");
        } catch (Exception e) {
            LOG.severe("cannot load file");
        }
    }
}
