package com.test.signup.api.model;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class GoogleLocationWithCoordinatesApiModel extends GoogleLocationApiModel {

    @JsonField(name = "location")
    private LocationCoordinatesApiModel locationCoordinates;

    @NonNull
    public LocationCoordinatesApiModel getLocationCoordinates() {
        return locationCoordinates;
    }

    void setLocationCoordinates(@NonNull final LocationCoordinatesApiModel locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

}
