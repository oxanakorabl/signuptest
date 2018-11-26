package com.test.signup.api.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class LocationCoordinatesApiModel {

    @JsonField(name = "longitude")
    private double longitude;
    @JsonField(name = "latitude")
    private double latitude;

    public double getLatitude() {
        return latitude;
    }

    void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

}
