package com.test.signup.api.model.response.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@SuppressWarnings("CPD-START")
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class BaseListResponse<T> {

    @JsonField(name = "data")
    private List<T> data;
    @JsonField(name = "code")
    private int code;
    @JsonField(name = "message")
    private String message;

    @NonNull
    public List<T> getData() {
        return data;
    }

    void setData(@NonNull final List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    void setCode(final int code) {
        this.code = code;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    void setMessage(@Nullable final String message) {
        this.message = message;
    }

}

