package com.test.signup.api.apipart.wrappers;

import android.support.annotation.NonNull;

import com.test.signup.api.apipart.AccountsApi;
import com.test.signup.api.apipart.wrappers.base.ApiWrapper;
import com.test.signup.api.model.AccessTokenApiModel;
import com.test.signup.api.model.LoginProvider;
import com.test.signup.api.model.response.AnonymousResponse;
import com.test.signup.api.model.response.AuthorizationResponse;
import com.test.signup.api.model.response.EmptyResponse;
import com.test.signup.api.model.response.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


public class AccountsApiWrapper {

    @NonNull
    private final ApiWrapper apiWrapper;
    @NonNull
    private final AccountsApi accountsApi;

    public AccountsApiWrapper(@NonNull final ApiWrapper apiWrapper,
                              @NonNull final AccountsApi accountsApi) {
        this.apiWrapper = apiWrapper;
        this.accountsApi = accountsApi;
    }

    @NonNull
    public Observable<List<LoginProvider>> getLoginProviders() {
        return apiWrapper.makeListRequestWithAuthHeader(authHeader -> accountsApi.getLoginProviders(authHeader).toObservable());
    }

    @NonNull
    public Observable<AuthorizationResponse> sendFacebookLoginInfo(@NonNull final AccessTokenApiModel accessTokenApiModel) {
        return accountsApi.sendFacebookLoginInfo(accessTokenApiModel).toObservable()
                .map(BaseResponse::getData);
    }

    @NonNull
    public Observable<EmptyResponse> deleteAccount() {
        return apiWrapper.makeRequestWithAuthHeader(authHeader -> accountsApi.deleteAccount(authHeader).toObservable());
    }

    @NonNull
    public Observable<AnonymousResponse> createNewAccount(@NonNull final String authHeader) {
        return recoverAccount(authHeader, true);
    }

    @NonNull
    public Observable<AnonymousResponse> reactivateAccount(@NonNull final String authHeader) {
        return recoverAccount(authHeader, false);
    }

    @NonNull
    private Observable<AnonymousResponse> recoverAccount(@NonNull final String authHeader, final boolean createNew) {
        return accountsApi.recoverAccount(authHeader, createNew).map(BaseResponse::getData).toObservable();
    }

}
