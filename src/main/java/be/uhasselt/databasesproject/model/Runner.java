package be.uhasselt.databasesproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Runner implements Serializable {

    private int id;
    private String firstName;
    private String familyName;
    private int age;
    private double weight;
    private double length;
    private String streetName;
    private String houseNumber;
    private String boxNumber;
    private String postalCode;
    private String city;
    private String country;

    public Runner() {
    }

    public Runner(int id, String firstName, String familyName, int age, double weight, double length, String streetName, String houseNumber, String boxNumber, String postalCode, String city, String country) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.age = age;
        this.weight = weight;
        this.length = length;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.boxNumber = boxNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + familyName;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Runner.class) return false;
        Runner runner = (Runner) object;
        return runner.id == this.id && runner.firstName.equals(this.firstName) && runner.familyName.equals(this.familyName) && runner.age == this.age && runner.weight == weight && runner.length == length && runner.streetName.equals(streetName) && runner.houseNumber.equals(houseNumber) && Objects.equals(runner.boxNumber, boxNumber) && runner.postalCode.equals(postalCode) && runner.city.equals(city) && runner.country.equals(country);
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getLength() {
        return length;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
