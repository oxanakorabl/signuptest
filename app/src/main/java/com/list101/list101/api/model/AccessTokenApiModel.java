package com.list101.list101.api.model;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import java.util.Date;
import java.util.Set;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class AccessTokenApiModel {

    @JsonField(name = "userId")
    private String userId;
    @JsonField(name = "appId")
    private String appId;
    @JsonField(name = "authenticationToken")
    private String authenticationToken;
    @JsonField(name = "refreshDate")
    private Date refreshDate;
    @JsonField(name = "expirationDate")
    private Date expirationDate;
    @JsonField(name = "grantedPermissions")
    private Set<String> grantedPermissions;
    @JsonField(name = "declinedPermissions")
    private Set<String> declinedPermissions;

    public AccessTokenApiModel() {
        // needed for json parsing
    }

    public AccessTokenApiModel(@NonNull final LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();
        userId = accessToken.getUserId();
        appId = accessToken.getApplicationId();
        authenticationToken = accessToken.getToken();
        refreshDate = accessToken.getLastRefresh();
        expirationDate = accessToken.getExpires();
        grantedPermissions = loginResult.getRecentlyGrantedPermissions();
        declinedPermissions = loginResult.getRecentlyDeniedPermissions();
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull final String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getAppId() {
        return appId;
    }

    public void setAppId(@NonNull final String appId) {
        this.appId = appId;
    }

    @NonNull
    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(@NonNull final String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @NonNull
    public Date getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(@NonNull final Date refreshDate) {
        this.refreshDate = refreshDate;
    }

    @NonNull
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@NonNull final Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @NonNull
    public Set<String> getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(@NonNull final Set<String> grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
    }

    @NonNull
    public Set<String> getDeclinedPermissions() {
        return declinedPermissions;
    }

    public void setDeclinedPermissions(@NonNull final Set<String> declinedPermissions) {
        this.declinedPermissions = declinedPermissions;
    }

}
