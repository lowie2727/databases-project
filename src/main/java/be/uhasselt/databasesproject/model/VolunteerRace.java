package be.uhasselt.databasesproject.model;

import java.util.Objects;

public class VolunteerRace {

    private int volunteerId;
    private int raceId;

    public VolunteerRace() {
    }

    public VolunteerRace(int volunteerId, int raceId) {
        this.volunteerId = volunteerId;
        this.raceId = raceId;
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
        return volunteerId == volunteerRace.volunteerId && raceId == volunteerRace.raceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteerId, raceId);
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
}
