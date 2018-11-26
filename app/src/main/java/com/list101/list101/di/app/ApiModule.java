package com.list101.list101.di.app;

import android.support.annotation.NonNull;

import com.list101.list101.api.apipart.AccountsApi;
import com.list101.list101.api.apipart.GeoApi;
import com.list101.list101.api.apipart.SignUpApi;
import com.list101.list101.api.apipart.wrappers.AccountsApiWrapper;
import com.list101.list101.api.apipart.wrappers.GeoApiWrapper;
import com.list101.list101.api.apipart.wrappers.base.ApiWrapper;
import com.list101.list101.di.qualifiers.GlobalRetrofit;
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences;
import com.list101.list101.storage.prefs.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @NonNull
    @Provides
    @Singleton
    ApiWrapper getApiWrapper(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences) {
        return new ApiWrapper(sessionPreferences);
    }

    @NonNull
    @Provides
    @Singleton
    AccountsApiWrapper getAccountsWrapper(@NonNull final ApiWrapper apiWrapper,
                                          @NonNull final AccountsApi accountsApi) {
        return new AccountsApiWrapper(apiWrapper, accountsApi);
    }


    @NonNull
    @Provides
    @Singleton
    SignUpApi getSignUpApi(@GlobalRetrofit @NonNull final Retrofit retrofit) {
        return retrofit.create(SignUpApi.class);
    }


    @NonNull
    @Provides
    @Singleton
    GeoApi getGeoApi(@GlobalRetrofit @NonNull final Retrofit retrofit) {
        return retrofit.create(GeoApi.class);
    }

    @NonNull
    @Provides
    @Singleton
    GeoApiWrapper getGeoApiWrapper(@NonNull final ApiWrapper apiWrapper,
                                   @NonNull final GeoApi geoApi) {
        return new GeoApiWrapper(apiWrapper, geoApi);
    }

}
