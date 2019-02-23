package ru.samsung.httprequestsbasics.model.entities;

import java.io.Serializable;

public class Geo implements Serializable {
    protected double lat;

    protected double lng;

    public Geo() {
    }

    public Geo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
