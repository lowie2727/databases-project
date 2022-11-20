package be.uhasselt.databasesproject.model;

public class Loper {

    private int id;
    private String voornaam;
    private String achternaam;
    private int leeftijd;
    private double gewicht;
    private double lengte;
    private String straatNaam;
    private String huisnummer;
    private String bus;
    private String postcode;
    private String stad;
    private String land;

    public Loper() {

    }

    public Loper(int id, String voornaam, String achternaam, int leeftijd, double gewicht, double lengte, String straatnaam, String huisnummer, String bus, String postcode, String stad, String land) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.leeftijd = leeftijd;
        this.gewicht = gewicht;
        this.lengte = lengte;
        this.straatNaam = straatnaam;
        this.huisnummer = huisnummer;
        this.bus = bus;
        this.postcode = postcode;
        this.stad = stad;
        this.land = land;
    }

    @Override
    public String toString() {
        return id + " " + voornaam + " " + achternaam;
    }

    public int getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public double getGewicht() {
        return gewicht;
    }

    public double getLengte() {
        return lengte;
    }

    public String getStraatNaam() {
        return straatNaam;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getBus() {
        return bus;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStad() {
        return stad;
    }

    public String getLand() {
        return land;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public void setLengte(double lengte) {
        this.lengte = lengte;
    }

    public void setStraatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public void setLand(String land) {
        this.land = land;
    }
}
