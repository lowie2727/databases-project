package be.uhasselt.databasesproject.model;

import java.util.Objects;

public class Volunteer {

    private int id;
    private String firstName;
    private String familyName;
    private String job;

    public Volunteer() {
    }

    public Volunteer(int id, String firstName, String familyName, String job) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.job = job;
    }

    @Override
    public String toString() {
        return String.format("Volunteer{id: %d, firstName: '%s', familyName: '%s'}", id, firstName, familyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && firstName.equals(volunteer.firstName) && familyName.equals(volunteer.familyName) && job.equals(volunteer.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, familyName, job);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
