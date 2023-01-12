package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class GlobalRanking implements Serializable {

    private int runnerId;
    private double prizeMoney;
    private double averageSpeed;

    public GlobalRanking() {
    }

    public GlobalRanking(int runnerId, double prizeMoney, double averageSpeed) {
        this.runnerId = runnerId;
        this.prizeMoney = prizeMoney;
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return String.format("GlobalRanking{runnerId: %d; prizeMoney: %f, totalTime: %f}", runnerId, prizeMoney, averageSpeed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalRanking globalRanking = (GlobalRanking) o;
        return runnerId == globalRanking.runnerId && Double.compare(globalRanking.prizeMoney, prizeMoney) == 0 && averageSpeed == globalRanking.averageSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(runnerId, prizeMoney, averageSpeed);
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
