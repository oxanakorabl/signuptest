package com.test.signup.mvp.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.test.signup.TestApp;
import com.test.signup.api.model.GoogleLocationWithCoordinatesApiModel;
import com.test.signup.api.model.response.SearchPlacesResponse;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.interfaces.GoogleLocationView;
import com.test.signup.mvp.model.locations.GoogleLocationWithCoordinates;

import java.util.List;

import io.reactivex.Observable;

public class GoogleCityLocationPresenter extends GoogleLocationPresenter<GoogleLocationView<GoogleLocationWithCoordinates>,
        GoogleLocationWithCoordinates> {

    @Nullable
    private final String signUpAuthHeader;

    public GoogleCityLocationPresenter(@NonNull final RxLifecycle rxLifecycle, @Nullable final String signUpAuthHeader) {
        super(rxLifecycle);

        this.signUpAuthHeader = signUpAuthHeader;
        TestApp.getDependenciesScopesController().getAppComponent().inject(this);
    }

    @Override
    @NonNull
    protected Observable<List<GoogleLocationWithCoordinates>> getSearchObservable(@NonNull final String query,
                                                                                  @Nullable final Double latitude,
                                                                                  @Nullable final Double longitude) {
        return getCitiesOnSignUp(query, latitude, longitude)
                .map(SearchPlacesResponse::getPlaces)
                .flatMapIterable(places -> places)
                .map(GoogleLocationWithCoordinates::new)
                .toList()
                .toObservable();
    }

    @NonNull
    private Observable<SearchPlacesResponse<GoogleLocationWithCoordinatesApiModel>> getCitiesOnSignUp(final @NonNull String query,
                                                                                                      final @Nullable Double latitude,
                                                                                                      final @Nullable Double longitude) {
        return geoApiWrapper.getCities(signUpAuthHeader, query, latitude, longitude);
    }

}
