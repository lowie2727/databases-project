package be.uhasselt.databasesproject.model;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class RunnerSegment {
    private String location;
    private int distance;
    private int time;
    private String timeString;
    private double averageSpeed;

    public RunnerSegment() {
    }

    public RunnerSegment(String location, int distance, int time, double averageSpeed) {
        this.location = location;
        this.distance = distance;
        this.time = time;
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return String.format("RunnerSegment{location: %s, distance: %d, time: %d, averageSpeed: %f}", location, distance, time, averageSpeed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunnerSegment runnerSegment = (RunnerSegment) o;
        return Objects.equals(location, runnerSegment.location) && Objects.equals(distance, runnerSegment.distance) && time == runnerSegment.time && averageSpeed == runnerSegment.averageSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, distance, time, averageSpeed);
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

    public void setFamilyName(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeString() {
        try {
            return new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("ss").parse("" + time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public double getAverageSpeed() {
        averageSpeed = Math.round(averageSpeed * 10);
        averageSpeed = averageSpeed / 10;
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
