package com.list101.list101.mvp.model.locations;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.list101.list101.api.model.LocationCoordinatesApiModel;

import org.parceler.Parcel;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
@Parcel(Parcel.Serialization.BEAN)
public class LocationCoordinates {
    @JsonField(name = "longitude")
    private double longitude;
    @JsonField(name = "latitude")
    private double latitude;

    public LocationCoordinates() {
        super();
    }

    public LocationCoordinates(@NonNull final LocationCoordinatesApiModel locationCoordinatesApiModel) {
        this.longitude = locationCoordinatesApiModel.getLongitude();
        this.latitude = locationCoordinatesApiModel.getLatitude();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

}
