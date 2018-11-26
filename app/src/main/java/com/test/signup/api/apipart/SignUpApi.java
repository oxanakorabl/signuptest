package com.test.signup.api.apipart;

import android.support.annotation.NonNull;

import com.test.signup.api.model.response.AnonymousResponse;
import com.test.signup.api.model.LocationApiModel;
import com.test.signup.api.model.response.ScoresResponse;
import com.test.signup.api.model.response.base.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignUpApi {

    @NonNull
    @GET("signup/location")
    Single<BaseResponse<LocationApiModel>> getLocationByCoordinates(@NonNull @Header(AccountsApi.AUTHORIZATION) String authHeader,
                                                                    @Query("lt") final double latitude,
                                                                    @Query("ln") final double longitude);

    @NonNull
    @POST("signup/location")
    Single<BaseResponse<AnonymousResponse>> acceptLocation(@NonNull @Header(AccountsApi.AUTHORIZATION) String authHeader);

    @NonNull
    @GET("signup/scores")
    Single<BaseResponse<ScoresResponse>> getScores(@NonNull @Header(AccountsApi.AUTHORIZATION) String authHeader);

    @NonNull
    @POST("signup/scores")
    Single<BaseResponse<AnonymousResponse>> acceptScores(@NonNull @Header(AccountsApi.AUTHORIZATION) String authHeader);

}
