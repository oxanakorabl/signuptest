package com.test.signup.api.apipart;

import android.support.annotation.NonNull;

import com.test.signup.api.model.AccessTokenApiModel;
import com.test.signup.api.model.LoginProvider;
import com.test.signup.api.model.response.AnonymousResponse;
import com.test.signup.api.model.response.AuthorizationResponse;
import com.test.signup.api.model.response.EmptyResponse;
import com.test.signup.api.model.response.base.BaseListResponse;
import com.test.signup.api.model.response.base.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountsApi {

    String AUTHORIZATION = "Authorization";

    @NonNull
    @GET("account/logins")
    Single<BaseListResponse<LoginProvider>> getLoginProviders(@NonNull @Header(AUTHORIZATION) String authHeader);

    @NonNull
    @POST("account/fb-login")
    Single<BaseResponse<AuthorizationResponse>> sendFacebookLoginInfo(@NonNull @Body final AccessTokenApiModel accessTokenApiModel);

    @NonNull
    @GET("account/delete")
    Single<BaseResponse<EmptyResponse>> deleteAccount(@NonNull @Header(AUTHORIZATION) String authHeader);

    @NonNull
    @GET("account/recover")
    Single<BaseResponse<AnonymousResponse>> recoverAccount(@NonNull @Header(AUTHORIZATION) String authHeader,
                                                           @Query("new") final boolean createNew);

}
