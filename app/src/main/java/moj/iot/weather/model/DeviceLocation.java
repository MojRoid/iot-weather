package moj.iot.weather.model;

public class DeviceLocation {

    private final double latitude;
    private final double longitude;

    public DeviceLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
