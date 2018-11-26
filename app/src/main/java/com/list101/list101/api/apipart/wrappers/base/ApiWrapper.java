package com.list101.list101.api.apipart.wrappers.base;

import android.support.annotation.NonNull;

import com.list101.list101.api.model.response.EmptyResponse;
import com.list101.list101.api.model.response.base.BaseListResponse;
import com.list101.list101.api.model.response.base.BaseResponse;
import com.list101.list101.exceptions.UnexpectedException;
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences;
import com.list101.list101.storage.prefs.Session;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ApiWrapper {

    @NonNull
    private final ReactiveJsonSharedPreferences<Session> sessionPreferences;

    public ApiWrapper(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences) {
        this.sessionPreferences = sessionPreferences;
    }

    @NonNull
    public <T> Observable<BaseResponse<T>> makeRequestWithAuthHeaderAndGetBaseResponse(
            @NonNull final Function<String, Observable<BaseResponse<T>>> func) {
        return getSessionWrappedObservable(func)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public <T, TType extends BaseResponse<T>> Observable<T> makeRequestWithAuthHeader(@NonNull final Function<String, Observable<TType>> func) {
        return getSessionWrappedObservable(func)
                .map(BaseResponse::getData)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public <TType extends EmptyResponse> Observable<TType> makeRequestWithAuthHeaderWithEmptyResponse(
            @NonNull final Function<String, Observable<TType>> func) {
        return getSessionWrappedObservable(func)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public <T, TType extends BaseListResponse<T>> Observable<List<T>> makeListRequestWithAuthHeader(@NonNull final Function<String,
            Observable<TType>> func) {
        return getSessionWrappedObservable(func)
                .map(BaseListResponse::getData)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    private <TType> Observable<TType> getSessionWrappedObservable(@NonNull final Function<String, Observable<TType>> func) {
        return sessionPreferences.getAsync()
                .switchMap(sessionOptional -> {
                    if (!sessionOptional.isPresent()) {
                        throw new UnexpectedException("session should be present");
                    }
                    return func.apply(sessionOptional.get().getAuthHeader());
                });
    }

}
