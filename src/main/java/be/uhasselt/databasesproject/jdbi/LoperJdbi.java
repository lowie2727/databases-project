package be.uhasselt.databasesproject.jdbi;

import be.uhasselt.databasesproject.model.Loper;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class LoperJdbi {

    private Jdbi jdbi;

    public LoperJdbi(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Loper> getLoperByName(String loper) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM loper WHERE voornaam = :voornaam")
                .bind("voornaam", loper)
                .mapToBean(Loper.class)
                .list());
    }

    public List<Loper> getLopers() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM loper")
                .mapToBean(Loper.class)
                .list());
    }

    public void insertLoper(Loper loper) {
        jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO \"loper\" (id, \"voornaam\", \"achternaam\", leeftijd, gewicht, lengte, \"straatnaam\", \"huisnummer\", \"bus\", \"postcode\", \"stad\", \"land\") VALUES (:id, :voornaam, :achternaam, :leeftijd, :gewicht, :lengte, :straatNaam, :huisnummer, :bus, :postcode, :stad, :land)")
                .bindBean(loper)
                .execute());
    }
}
