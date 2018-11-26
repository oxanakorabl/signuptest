package com.test.signup.api.model;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.test.signup.api.model.enums.LoginProviderType;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class LoginProvider {

    @JsonField(name = "provider")
    private String provider;
    @JsonField(name = "state")
    private String state;
    @JsonField(name = "login_uri")
    private String loginUri;

    @LoginProviderType
    @NonNull
    public String getProvider() {
        return provider;
    }

    void setProvider(@NonNull final String provider) {
        this.provider = provider;
    }

    @NonNull
    public String getState() {
        return state;
    }

    void setState(@NonNull final String state) {
        this.state = state;
    }

    @NonNull
    public String getLoginUri() {
        return loginUri;
    }

    void setLoginUri(@NonNull final String loginUri) {
        this.loginUri = loginUri;
    }

}
