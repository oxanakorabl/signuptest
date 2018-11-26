package com.test.signup.mvp.model.locations;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.test.signup.api.model.GoogleLocationWithCoordinatesApiModel;

import org.parceler.Parcel;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
@Parcel(Parcel.Serialization.BEAN)
public class GoogleLocationWithCoordinates extends GoogleLocation {

    @NonNull
    @JsonField(name = "coordinates")
    private LocationCoordinates locationCoordinates;

    public GoogleLocationWithCoordinates() {
        super();
    }

    public GoogleLocationWithCoordinates(@NonNull final GoogleLocationWithCoordinatesApiModel googleLocationWithCoordinatesApiModel) {
        super(googleLocationWithCoordinatesApiModel);

        this.locationCoordinates = new LocationCoordinates(googleLocationWithCoordinatesApiModel.getLocationCoordinates());
    }

    public GoogleLocationWithCoordinates(@NonNull final GoogleLocation model) {
        super(model);
        locationCoordinates = new LocationCoordinates();
    }

    @NonNull
    public LocationCoordinates getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(@NonNull final LocationCoordinates locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

}
