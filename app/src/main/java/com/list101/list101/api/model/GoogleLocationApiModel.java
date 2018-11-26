package com.list101.list101.api.model;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.list101.list101.mvp.model.locations.GoogleLocation;

import org.parceler.Parcel;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
@Parcel(Parcel.Serialization.BEAN)
public class GoogleLocationApiModel {

    @JsonField(name = "primary_name")
    private String primaryName;
    @JsonField(name = "secondary_name")
    private String secondaryName;
    @JsonField(name = "place_id")
    private String placeId;

    GoogleLocationApiModel() {
        // Needed for Parcel
    }

    public GoogleLocationApiModel(@NonNull final GoogleLocation googleLocation) {
        primaryName = googleLocation.getPrimaryName();
        secondaryName = googleLocation.getSecondaryName();
        placeId = googleLocation.getPlaceId();
    }

    @NonNull
    public String getPrimaryName() {
        return primaryName;
    }

    @NonNull
    public String getSecondaryName() {
        return secondaryName;
    }

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    void setPrimaryName(@NonNull final String primaryName) {
        this.primaryName = primaryName;
    }

    void setSecondaryName(@NonNull final String secondaryName) {
        this.secondaryName = secondaryName;
    }

    void setPlaceId(@NonNull final String placeId) {
        this.placeId = placeId;
    }

}
