package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Volunteer implements Serializable {

    private int id;
    private String firstName;
    private String familyName;
    private String job;
    private String password;

    public Volunteer() {
    }

    public Volunteer(int id, String firstName, String familyName, String job, String password) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.job = job;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, familyName);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
