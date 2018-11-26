package com.list101.list101.api.model;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class LocationApiModel {

    @JsonField(name = "location")
    private String location;
    @JsonField(name = "acceptable")
    private boolean acceptable;
    @JsonField(name = "session_location_id")
    private String sessionLocationId;

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull final String location) {
        this.location = location;
    }

    public boolean isAcceptable() {
        return acceptable;
    }

    void setAcceptable(final boolean acceptable) {
        this.acceptable = acceptable;
    }

    @NonNull
    public String getSessionLocationId() {
        return sessionLocationId;
    }

    void setSessionLocationId(@NonNull final String sessionLocationId) {
        this.sessionLocationId = sessionLocationId;
    }

}
