package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Segment implements Serializable {

    private int id;
    private int raceId;
    private String location;
    private int distance;

    public Segment() {
    }

    public Segment(int id, int raceId, String location, int distance) {
        this.id = id;
        this.raceId = raceId;
        this.location = location;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.format("Segment{id: %d, raceId: %d, location: '%s'}", id, raceId, location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment = (Segment) o;
        return id == segment.id && raceId == segment.raceId && distance == segment.distance && location.equals(segment.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, raceId, location, distance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
