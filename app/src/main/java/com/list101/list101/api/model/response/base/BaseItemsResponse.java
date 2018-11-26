package com.list101.list101.api.model.response.base;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class BaseItemsResponse<T> {

    @JsonField(name = "items")
    private List<T> items;
    @JsonField(name = "next_page")
    private String nextPageId;

    @NonNull
    public List<T> getItems() {
        return items;
    }

    void setItems(@Nullable final List<T> items) {
        this.items = items;
    }

    @NonNull
    public String getNextPageId() {
        return nextPageId;
    }

    void setNextPageId(@Nullable final String nextPageId) {
        this.nextPageId = nextPageId;
    }

}
