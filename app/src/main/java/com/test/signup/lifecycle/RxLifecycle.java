package com.test.signup.lifecycle;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface RxLifecycle {

    /**
     * Method should be used to subscribe to observable while this element is in started state.
     * Passed observable should NOT emit errors. It is illegal as in that case it stops emitting items and binding lost after error.
     *
     * @return {@link Disposable} which is represents binding. If you want to stop binding then just call {@link Disposable#dispose()} ()} on it.
     */
    @NonNull
    <T> Disposable bind(@NonNull Observable<T> observable,
                        @NonNull Consumer<T> onNextConsumer);

    @NonNull
    <T> Disposable untilStop(@NonNull Observable<T> observable);

    @NonNull
    <T> Disposable untilStop(@NonNull Observable<T> observable,
                             @NonNull Consumer<T> onNextConsumer);

    @NonNull
    <T> Disposable untilStop(@NonNull Observable<T> observable,
                             @NonNull Consumer<T> onNextConsumer,
                             @NonNull Consumer<Throwable> onErrorConsumer);

    @NonNull
    <T> Disposable untilStop(@NonNull Observable<T> observable,
                             @NonNull Consumer<T> onNextConsumer,
                             @NonNull Consumer<Throwable> onErrorConsumer,
                             @NonNull Action onCompleteAction);

    @NonNull
    <T> Disposable untilDestroy(@NonNull Observable<T> observable);

    @NonNull
    <T> Disposable untilDestroy(@NonNull Observable<T> observable,
                                @NonNull Consumer<T> onNextConsumer);

    @NonNull
    <T> Disposable untilDestroy(@NonNull Observable<T> observable,
                                @NonNull Consumer<T> onNextConsumer,
                                @NonNull Consumer<Throwable> onErrorConsumer);


    @NonNull
    <T> Disposable untilDestroy(@NonNull Observable<T> observable,
                                @NonNull Consumer<T> onNextConsumer,
                                @NonNull Consumer<Throwable> onErrorConsumer,
                                @NonNull Action onCompleteAction);

}
