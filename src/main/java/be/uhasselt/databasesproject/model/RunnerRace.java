package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class RunnerRace implements Serializable {

    private int runnerId;
    private int raceId;
    private int shirtNumber;
    private int time;

    public RunnerRace() {
    }

    public RunnerRace(int runnerId, int raceId, int shirtNumber, int time) {
        this.runnerId = runnerId;
        this.raceId = raceId;
        this.shirtNumber = shirtNumber;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("RunnerRace{runnerId: %d, raceId: %d, shirtNumber: %d}", runnerId, raceId, shirtNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunnerRace runnerRace = (RunnerRace) o;
        return runnerId == runnerRace.runnerId && raceId == runnerRace.raceId && shirtNumber == runnerRace.shirtNumber && time == runnerRace.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(runnerId, raceId, shirtNumber, time);
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
