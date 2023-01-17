package be.uhasselt.databasesproject.model;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class RunnerRanking {

    private int rank;
    private String firstName;
    private String familyName;
    private double prizeMoney;
    private int time;
    private String timeString;
    private double averageSpeed;

    public RunnerRanking() {
    }

    public RunnerRanking(String firstName, String familyName, int time, double averageSpeed) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.time = time;
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return String.format("RunnerRanking{first name: %s, family name: %s, time: %d, averageSpeed: %f}", firstName, familyName, time, averageSpeed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunnerRanking runnerRanking = (RunnerRanking) o;
        return Objects.equals(firstName, runnerRanking.firstName) && Objects.equals(familyName, runnerRanking.familyName) && time == runnerRanking.time && averageSpeed == runnerRanking.averageSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, familyName, time, averageSpeed);
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

    public void setPrizeMoney(double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
