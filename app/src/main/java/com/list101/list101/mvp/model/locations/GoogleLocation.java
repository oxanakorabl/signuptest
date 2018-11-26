package com.list101.list101.mvp.model.locations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.api.model.GoogleLocationApiModel;
import com.list101.list101.mvp.model.interfaces.SearchText;
import com.list101.list101.views.searchchooserwidget.SearchWidgetModel;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class GoogleLocation implements SearchText, SearchWidgetModel {

    @NonNull
    private String primaryName;
    @NonNull
    private String secondaryName;
    @NonNull
    private String placeId;

    public GoogleLocation() {
        super();
    }

    public GoogleLocation(@NonNull final GoogleLocationApiModel googleLocationApiModel) {
        primaryName = googleLocationApiModel.getPrimaryName();
        secondaryName = googleLocationApiModel.getSecondaryName();
        placeId = googleLocationApiModel.getPlaceId();
    }

    public GoogleLocation(@NonNull final GoogleLocation model) {
        primaryName = model.getPrimaryName();
        secondaryName = model.getSecondaryName();
        placeId = model.getPlaceId();
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
    @Override
    public String getSearchText() {
        return primaryName;
    }

    @NonNull
    @Override
    public String getName() {
        return primaryName;
    }

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    @Override
    public String getId() {
        return placeId;
    }

    public void setPrimaryName(@NonNull final String primaryName) {
        this.primaryName = primaryName;
    }

    public void setSecondaryName(@NonNull final String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setPlaceId(@NonNull final String placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final GoogleLocation that = (GoogleLocation) object;
        return placeId.equals(that.placeId);
    }

    @Override
    public int hashCode() {
        return placeId.hashCode();
    }

}
