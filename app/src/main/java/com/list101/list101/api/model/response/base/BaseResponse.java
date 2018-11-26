package com.list101.list101.api.model.response.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class BaseResponse<TData> {

    @JsonField(name = "data")
    private TData data;
    @JsonField(name = "code")
    private int code;
    @JsonField(name = "message")
    private String message;

    @NonNull
    public TData getData() {
        return data;
    }

    void setData(@NonNull final TData data) {
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