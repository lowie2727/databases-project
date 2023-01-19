package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Volunteer;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class VolunteerJdbi implements JdbiInterface<Volunteer> {

    private final Jdbi jdbi;

    public VolunteerJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    @Override
    public List<Volunteer> getAll() {
        String query = "SELECT * FROM volunteer";
        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(Volunteer.class)
                .list());
    }

    @Override
    public void insert(Volunteer volunteer) {
        String query = "INSERT INTO volunteer (firstName, familyName, username, password) " +
                "VALUES (:firstName, :familyName, :username, :password)";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteer)
                .execute());
    }

    @Override
    public void update(Volunteer volunteer) {
        String query = "UPDATE volunteer SET firstName = :firstName, familyName = :familyName " +
                "WHERE id = :id";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteer)
                .execute());
    }

    @Override
    public void delete(Volunteer volunteer) {
        String query = "DELETE FROM volunteer " +
                "WHERE id = :id";

        jdbi.withHandle(handle -> handle.createUpdate(query)
                .bindBean(volunteer)
                .execute());
    }

    public Volunteer getById(int id) {
        String query = "SELECT * FROM volunteer " +
                "WHERE id = :id";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("id", id)
                .mapToBean(Volunteer.class)
                .one());
    }

    public String getHashedPassword(int id) {
        String query = "SELECT password FROM volunteer " +
                "WHERE id = :id";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("id", id)
                .mapTo(String.class)
                .one());
    }

    public boolean doesUsernameExists(String username) {
        String query = "SELECT CASE WHEN (SELECT count(*) FROM volunteer " +
                "WHERE volunteer.username = :username) > 0 THEN 1 ELSE 0 END";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("username", username)
                .mapTo(Boolean.class)
                .one());
    }

    public Volunteer getByUsername(String username) {
        String query = "SELECT * FROM volunteer " +
                "WHERE username = :username";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("username", username)
                .mapToBean(Volunteer.class)
                .one());
    }

    public List<String> getAllUsernames() {
        String query = "SELECT username FROM volunteer";

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapTo(String.class)
                .list());
    }
}
