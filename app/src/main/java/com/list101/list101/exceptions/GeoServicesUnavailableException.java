package com.list101.list101.exceptions;

import android.support.annotation.NonNull;

public class GeoServicesUnavailableException extends Exception {

    public GeoServicesUnavailableException() {
        super();
    }

    public GeoServicesUnavailableException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}