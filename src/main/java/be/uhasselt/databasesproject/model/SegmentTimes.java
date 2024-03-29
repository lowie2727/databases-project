package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class SegmentTimes implements Serializable {

    private int segmentId;
    private int runnerId;
    private int time;
    private String timeString;

    public SegmentTimes() {
    }

    public SegmentTimes(int segmentId, int runnerId, int time) {
        this.segmentId = segmentId;
        this.runnerId = runnerId;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("SegmentTimes{segmentId: %d, runnerId: %d, time: %d}", segmentId, runnerId, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SegmentTimes segmentTimes = (SegmentTimes) o;
        return segmentId == segmentTimes.segmentId && runnerId == segmentTimes.runnerId && time == segmentTimes.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(segmentId, runnerId, time);
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
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
}
