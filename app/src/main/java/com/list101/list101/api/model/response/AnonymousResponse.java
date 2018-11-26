package com.list101.list101.api.model.response;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class AnonymousResponse extends AuthorizationResponse {

    @JsonField(name = "token_type")
    private String tokenType;

    @NonNull
    public String getTokenType() {
        return tokenType;
    }

    void setTokenType(@NonNull final String tokenType) {
        this.tokenType = tokenType;
    }

}
