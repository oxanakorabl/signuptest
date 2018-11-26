package com.test.signup.api.model.response;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.test.signup.api.model.enums.LoginStatus;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class AuthorizationResponse {

    @JsonField(name = "user_id")
    private String userId;
    @JsonField(name = "access_token")
    private String accessToken;
    @JsonField(name = "status")
    @LoginStatus
    private String status;
    @JsonField(name = ".issued")
    private long issuedDate;
    @JsonField(name = ".expires")
    private long expireDate;

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull final String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@NonNull final String accessToken) {
        this.accessToken = accessToken;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull final String status) {
        this.status = status;
    }

    public long getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(final long issuedDate) {
        this.issuedDate = issuedDate;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(final long expireDate) {
        this.expireDate = expireDate;
    }

}
