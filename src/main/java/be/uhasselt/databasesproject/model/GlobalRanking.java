package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class GlobalRanking implements Serializable {

    private int runnerId;
    private double prizeMoney;
    private int totalTime;

    public GlobalRanking() {
    }

    public GlobalRanking(int runnerId, double prizeMoney, int totalTime) {
        this.runnerId = runnerId;
        this.prizeMoney = prizeMoney;
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return String.format("GlobalRanking{runnerId: %d; prizeMoney: %f, totalTime: %d}", runnerId, prizeMoney, totalTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalRanking globalRanking = (GlobalRanking) o;
        return runnerId == globalRanking.runnerId && Double.compare(globalRanking.prizeMoney, prizeMoney) == 0 && totalTime == globalRanking.totalTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(runnerId, prizeMoney, totalTime);
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

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
