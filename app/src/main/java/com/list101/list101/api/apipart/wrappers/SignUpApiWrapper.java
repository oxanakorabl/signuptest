package com.list101.list101.api.apipart.wrappers;

import android.support.annotation.NonNull;

import com.list101.list101.api.apipart.SignUpApi;
import com.list101.list101.api.model.LocationApiModel;
import com.list101.list101.api.model.response.AnonymousResponse;
import com.list101.list101.api.model.response.ScoresResponse;
import com.list101.list101.api.model.response.base.BaseResponse;

import io.reactivex.Observable;

public class SignUpApiWrapper {

    @NonNull
    private final SignUpApi signUpApi;

    public SignUpApiWrapper(@NonNull final SignUpApi signUpApi) {
        this.signUpApi = signUpApi;
    }

    @NonNull
    public Observable<LocationApiModel> getLocationByCoordinates(@NonNull final String authHeader,
                                                                 final double latitude,
                                                                 final double longitude) {
        return signUpApi.getLocationByCoordinates(authHeader, latitude, longitude)
                .toObservable()
                .map(BaseResponse::getData);
    }

    @NonNull
    public Observable<AnonymousResponse> acceptLocation(@NonNull final String authHeader) {
        return signUpApi.acceptLocation(authHeader)
                .map(BaseResponse::getData)
                .toObservable();
    }

    @NonNull
    public Observable<ScoresResponse> getScores(@NonNull final String authHeader) {
        return signUpApi.getScores(authHeader)
                .map(BaseResponse::getData)
                .toObservable();
    }

    @NonNull
    public Observable<AnonymousResponse> acceptScores(@NonNull final String authHeader) {
        return signUpApi.acceptScores(authHeader)
                .map(BaseResponse::getData)
                .toObservable();
    }

}
