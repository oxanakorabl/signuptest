package com.list101.list101.api.model.response;

import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.list101.list101.api.model.SearchResultApiModel;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class SearchResultResponse {

    @JsonField(name = "results")
    private List<SearchResultApiModel> searchResultApiModelList;
    @JsonField(name = "after")
    private String afterId;

    @NonNull
    public List<SearchResultApiModel> getSearchResultApiModelList() {
        return searchResultApiModelList;
    }

    void setSearchResultApiModelList(@NonNull final List<SearchResultApiModel> searchResultApiModelList) {
        this.searchResultApiModelList = searchResultApiModelList;
    }

    @NonNull
    public String getAfterId() {
        return afterId;
    }

    void setAfterId(@NonNull final String afterId) {
        this.afterId = afterId;
    }

}
