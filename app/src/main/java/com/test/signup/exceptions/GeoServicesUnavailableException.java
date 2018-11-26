package com.test.signup.exceptions;

import android.support.annotation.NonNull;

public class GeoServicesUnavailableException extends Exception {

    public GeoServicesUnavailableException() {
        super();
    }

    public GeoServicesUnavailableException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}