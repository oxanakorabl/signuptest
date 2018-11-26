package com.list101.list101.lifecycle;

import android.support.annotation.NonNull;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public final class EmptyAction<T> implements Action, Consumer<T> {

    public static final EmptyAction EMPTY_ACTION = new EmptyAction();

    @Override
    public void run() throws Exception {
        // does nothing
    }

    @Override
    public void accept(@NonNull final T object) throws Exception {
        // does nothing
    }

}