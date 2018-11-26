package com.list101.list101.exceptions;

import android.support.annotation.NonNull;

/**
 * Exception that should be threw when some unexpected code reached.
 * For instance, if some value is null but it is not legal or in "default" case in switch all specific cases should be processed.
 */
public class UnexpectedException extends RuntimeException {

    private static final long serialVersionUID = 0;

    public UnexpectedException() {
        super();
    }

    public UnexpectedException(@NonNull final String detailMessage) {
        super(detailMessage);
    }

    public UnexpectedException(@NonNull final String detailMessage, @NonNull final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnexpectedException(@NonNull final Throwable throwable) {
        super(throwable);
    }

}