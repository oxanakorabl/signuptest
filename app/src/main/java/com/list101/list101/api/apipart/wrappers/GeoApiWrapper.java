package com.list101.list101.api.apipart.wrappers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.api.apipart.GeoApi;
import com.list101.list101.api.apipart.wrappers.base.ApiWrapper;
import com.list101.list101.api.model.GoogleLocationApiModel;
import com.list101.list101.api.model.GoogleLocationWithCoordinatesApiModel;
import com.list101.list101.api.model.response.SearchPlacesResponse;
import com.list101.list101.api.model.response.base.BaseResponse;

import io.reactivex.Observable;

public class GeoApiWrapper {

    @NonNull
    private final ApiWrapper apiWrapper;

    @NonNull
    private final GeoApi geoApi;

    public GeoApiWrapper(@NonNull final ApiWrapper apiWrapper,
                         @NonNull final GeoApi geoApi) {
        this.apiWrapper = apiWrapper;
        this.geoApi = geoApi;
    }

    @NonNull
    public Observable<SearchPlacesResponse<GoogleLocationWithCoordinatesApiModel>> getCities(@Nullable final String signUpAuthHeader,
                                                                                             @NonNull final String searchQuery,
                                                                                             @Nullable final Double latitude,
                                                                                             @Nullable final Double longitude) {
        if (signUpAuthHeader != null) {
            return geoApi.getCities(signUpAuthHeader, searchQuery, longitude, latitude)
                    .map(BaseResponse::getData)
                    .toObservable();
        } else {
            return apiWrapper.makeRequestWithAuthHeader(authHeader -> geoApi.getCities(authHeader, searchQuery, latitude, longitude).toObservable());
        }
    }

    @NonNull
    public Observable<SearchPlacesResponse<GoogleLocationApiModel>> getPlaces(@NonNull final String searchQuery,
                                                                              @Nullable final Double latitude,
                                                                              @Nullable final Double longitude) {
        return apiWrapper.makeRequestWithAuthHeader(authHeader -> geoApi.getPlaces(authHeader, searchQuery, latitude, longitude).toObservable());
    }

}
