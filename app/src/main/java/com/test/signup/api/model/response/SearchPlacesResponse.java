package com.test.signup.api.model.response;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class SearchPlacesResponse<T> {

    @JsonField(name = "places")
    private List<T> places;

    @NonNull
    public List<T> getPlaces() {
        return places;
    }

    void setPlaces(@NonNull final List<T> places) {
        this.places = places;
    }

}
