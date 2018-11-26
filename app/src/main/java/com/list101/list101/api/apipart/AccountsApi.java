package com.list101.list101.api.apipart;

import android.support.annotation.NonNull;

import com.list101.list101.api.model.AccessTokenApiModel;
import com.list101.list101.api.model.LoginProvider;
import com.list101.list101.api.model.response.AnonymousResponse;
import com.list101.list101.api.model.response.AuthorizationResponse;
import com.list101.list101.api.model.response.EmptyResponse;
import com.list101.list101.api.model.response.base.BaseListResponse;
import com.list101.list101.api.model.response.base.BaseResponse;

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
