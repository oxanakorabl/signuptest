package com.list101.list101.api.model.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class ScoresResponse {

    @JsonField(name = "influence")
    private long influencePoints;
    @JsonField(name = "popularity")
    private long popularityPoints;

    public long getInfluencePoints() {
        return influencePoints;
    }

    void setInfluencePoints(final long influencePoints) {
        this.influencePoints = influencePoints;
    }

    public long getPopularityPoints() {
        return popularityPoints;
    }

    void setPopularityPoints(final long popularityPoints) {
        this.popularityPoints = popularityPoints;
    }

}
