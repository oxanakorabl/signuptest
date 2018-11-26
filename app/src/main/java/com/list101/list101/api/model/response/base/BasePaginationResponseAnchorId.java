package com.list101.list101.api.model.response.base;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class BasePaginationResponseAnchorId<T> {
    @JsonField(name = "results")
    private List<T> list;
    @JsonField(name = "after")
    private String afterId;

    @NonNull
    public List<T> getList() {
        return list;
    }

    void setList(@NonNull final List<T> list) {
        this.list = list;
    }

    @NonNull
    public String getAfterId() {
        return afterId;
    }

    void setAfterId(@NonNull final String afterId) {
        this.afterId = afterId;
    }
}
