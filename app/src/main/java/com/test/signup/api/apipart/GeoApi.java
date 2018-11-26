package com.test.signup.api.apipart;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.test.signup.api.model.GoogleLocationApiModel;
import com.test.signup.api.model.GoogleLocationWithCoordinatesApiModel;
import com.test.signup.api.model.response.SearchPlacesResponse;
import com.test.signup.api.model.response.base.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GeoApi {

    @NonNull
    @GET("Geo/cities")
    Single<BaseResponse<SearchPlacesResponse<GoogleLocationWithCoordinatesApiModel>>> getCities(@NonNull @Header(AccountsApi.AUTHORIZATION)
                                                                                                              String authHeader,
                                                                                                @Query("q") @NonNull final String searchQuery,
                                                                                                @Query("lt") @Nullable final Double lt,
                                                                                                @Query("ln") @Nullable final Double ln);

    @NonNull
    @GET("Geo/places")
    Single<BaseResponse<SearchPlacesResponse<GoogleLocationApiModel>>> getPlaces(@NonNull @Header(AccountsApi.AUTHORIZATION) String authHeader,
                                                                                 @Query("q") @NonNull final String searchQuery,
                                                                                 @Query("lt") @Nullable final Double lt,
                                                                                 @Query("ln") @Nullable final Double ln);

}
