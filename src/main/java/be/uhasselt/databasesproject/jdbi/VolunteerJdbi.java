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
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM volunteer")
                .mapToBean(Volunteer.class)
                .list());
    }

    @Override
    public void insert(Volunteer volunteer) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO volunteer (firstName, familyName, password) VALUES (:firstName, :familyName, :password)")
                .bindBean(volunteer)
                .execute());
    }

    @Override
    public void update(Volunteer volunteer) {
        jdbi.withHandle(handle -> handle.createUpdate("UPDATE volunteer SET firstName = :firstName, familyName = :familyName WHERE id = :id")
                .bindBean(volunteer)
                .execute());
    }

    @Override
    public void delete(Volunteer volunteer) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM volunteer WHERE id = :id").bindBean(volunteer).execute());
    }

    public Volunteer getById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM volunteer WHERE id = :id")
                .bind("id", id)
                .mapToBean(Volunteer.class)
                .one());
    }

    public String getHashedPassword(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT password FROM volunteer WHERE id = :id")
                .bind("id", id)
                .mapTo(String.class)
                .one());
    }
}
