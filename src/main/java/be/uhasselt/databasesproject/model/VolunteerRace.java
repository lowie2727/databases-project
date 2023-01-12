package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class VolunteerRace implements Serializable {

    private int volunteerId;
    private int raceId;
    private String job;


    public VolunteerRace() {
    }

    public VolunteerRace(int volunteerId, int raceId, String job) {
        this.volunteerId = volunteerId;
        this.raceId = raceId;
        this.job = job;
    }

    @Override
    public String toString() {
        return String.format("VolunteerRace{volunteerId: %d, raceId: %d}", volunteerId, raceId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VolunteerRace volunteerRace = (VolunteerRace) o;
        return volunteerId == volunteerRace.volunteerId && raceId == volunteerRace.raceId && job.equals(volunteerRace.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteerId, raceId, job);
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
