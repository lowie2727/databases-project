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
    private String password;
    private String username;
    private String streetName;
    private String houseNumber;
    private String boxNumber;
    private String postalCode;
    private String city;
    private String country;

    public Runner() {
    }

    public Runner(int id, String firstName, String familyName, int age, double weight, double length, String password, String username, String streetName, String houseNumber, String boxNumber, String postalCode, String city, String country) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.age = age;
        this.weight = weight;
        this.length = length;
        this.password = password;
        this.username = username;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.boxNumber = boxNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, familyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Runner runner = (Runner) o;
        return id == runner.id && age == runner.age && Double.compare(runner.weight, weight) == 0 && Double.compare(runner.length, length) == 0 && firstName.equals(runner.firstName) && familyName.equals(runner.familyName) && streetName.equals(runner.streetName) && houseNumber.equals(runner.houseNumber) && Objects.equals(boxNumber, runner.boxNumber) && postalCode.equals(runner.postalCode) && city.equals(runner.city) && country.equals(runner.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, familyName, age, weight, length, streetName, houseNumber, boxNumber, postalCode, city, country);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
