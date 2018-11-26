package com.test.signup.storage.prefs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.test.signup.exceptions.UnexpectedException;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.ANNOTATIONS_ONLY)
public class Session {

    private static final String HEADER_VALUE_BEARER_PREFIX = "bearer ";

    @NonNull
    public static String makeAuthHeader(@NonNull final String accessToken) {
        return HEADER_VALUE_BEARER_PREFIX + accessToken;
    }

    @JsonField(name = "auth_header")
    private String authHeader;
    @JsonField(name = "user_id")
    private String userId;
    @JsonField(name = "logged_in")
    private boolean loggedIn;

    public Session() {
        super();
    }

    public Session(@NonNull final String accessToken, @Nullable final String userId, final boolean loggedIn) {
        this();

        this.authHeader = makeAuthHeader(accessToken);
        this.userId = userId;
        this.loggedIn = loggedIn;
    }

    @NonNull
    public String getAuthHeader() {
        return authHeader;
    }

    void setAuthHeader(@NonNull final String authHeader) {
        this.authHeader = authHeader;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getNonNullUserId() {
        if (userId != null) {
            return userId;
        } else {
            throw new UnexpectedException("userId is not supposed to be null when you call");
        }
    }

    void setUserId(@NonNull final String userId) {
        this.userId = userId;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    void setLoggedIn(final boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Session session = (Session) obj;

        return loggedIn == session.loggedIn
                && authHeader.equals(session.authHeader)
                && userId.equals(session.userId);
    }

    @Override
    public int hashCode() {
        int result = authHeader.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + (loggedIn ? 1 : 0);
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        return "Access token: " + authHeader + ",  userId: " + userId + " loggedIn: " + loggedIn;
    }

}
