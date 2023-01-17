package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Race implements Serializable {

    private int id;
    private String date;
    private String name;
    private int distance;
    private double distanceInKm;
    private double price;

    public Race() {
    }

    public Race(int id, String date, String name, int distance, double price) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.distance = distance;
        this.price = price;
    }

    private int meterToKilometer() {
        return distance / 1000;
    }

    @Override
    public String toString() {
        return String.format("%s %sK %s", name, meterToKilometer(), date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Race race = (Race) o;
        return id == race.id && distance == race.distance && Double.compare(race.price, price) == 0 && date.equals(race.date) && name.equals(race.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name, distance, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getDistanceInKm() {
        return ((double) distance) / 1000;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
