package com.list101.list101.lifecycle;

import android.support.annotation.NonNull;

import com.list101.list101.exceptions.UnexpectedException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

// TODO: do the same for Single and Completable
public class BaseRxLifecycle implements RxLifecycle {

    @NonNull
    private final BehaviorSubject<Boolean> onCreateSubject = BehaviorSubject.create();
    @NonNull
    private final BehaviorSubject<Boolean> onStartSubject = BehaviorSubject.create();

    public void onCreate() {
        onCreateSubject.onNext(true);
    }

    public void onStart() {
        onStartSubject.onNext(true);
    }

    public void onStop() {
        onStartSubject.onNext(false);
    }

    public void onDestroy() {
        onCreateSubject.onNext(false);
    }

    @NonNull
    @Override
    public <T> Disposable bind(@NonNull final Observable<T> observable, @NonNull final Consumer<T> onNextAction) {
        return onStartSubject.switchMap(started -> started ? observable.observeOn(AndroidSchedulers.mainThread()) : Observable.never())
                .takeUntil(onCreateSubject.filter(created -> !created))
                .subscribe(onNextAction,
                        throwable -> {
                            throw new UnexpectedException("Unexpected error on bind. Observable should NOT emit errors in 'bind' method!",
                                    throwable);
                        });
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> Disposable untilStop(@NonNull final Observable<T> observable) {
        return untilStop(observable, EmptyAction.EMPTY_ACTION,
                throwable -> {
                    throw new UnexpectedException("Unexpected error on untilStop with only Observable as an argument", throwable);
                }, EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilStop(@NonNull final Observable<T> observable, @NonNull final Consumer<T> onNextAction) {
        return untilStop(observable, onNextAction,
                throwable -> {
                    throw new UnexpectedException("Unexpected error on untilStop with Observable and onNext as arguments", throwable);
                },
                EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilStop(@NonNull final Observable<T> observable,
                                    @NonNull final Consumer<T> onNextAction,
                                    @NonNull final Consumer<Throwable> onErrorAction) {
        return untilStop(observable, onNextAction, onErrorAction, EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilStop(@NonNull final Observable<T> observable,
                                    @NonNull final Consumer<T> onNextAction,
                                    @NonNull final Consumer<Throwable> onErrorAction,
                                    @NonNull final Action onCompleteAction) {
        return until(observable, onStartSubject.map(started -> !started), onNextAction, onErrorAction, onCompleteAction);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> Disposable untilDestroy(@NonNull final Observable<T> observable) {
        return untilDestroy(observable, EmptyAction.EMPTY_ACTION,
                throwable -> {
                    throw new UnexpectedException("Unexpected error on untilDestroy with only Observable as an argument", throwable);
                }, EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilDestroy(@NonNull final Observable<T> observable,
                                       @NonNull final Consumer<T> onNextAction) {
        return untilDestroy(observable, onNextAction,
                throwable -> {
                    throw new UnexpectedException("Unexpected error on untilDestroy with Observable and onNext as arguments", throwable);
                }, EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilDestroy(@NonNull final Observable<T> observable,
                                       @NonNull final Consumer<T> onNextAction,
                                       @NonNull final Consumer<Throwable> onErrorAction) {
        return untilDestroy(observable, onNextAction, onErrorAction, EmptyAction.EMPTY_ACTION);
    }

    @NonNull
    @Override
    public <T> Disposable untilDestroy(@NonNull final Observable<T> observable,
                                       @NonNull final Consumer<T> onNextAction,
                                       @NonNull final Consumer<Throwable> onErrorAction,
                                       @NonNull final Action onCompleteAction) {
        return until(observable, onCreateSubject.map(created -> !created), onNextAction, onErrorAction, onCompleteAction);
    }

    @NonNull
    private <T> Disposable until(@NonNull final Observable<T> observable,
                                 @NonNull final Observable<Boolean> conditionSubject,
                                 @NonNull final Consumer<T> onNextAction,
                                 @NonNull final Consumer<Throwable> onErrorAction,
                                 @NonNull final Action onCompleteAction) {
        return onCreateSubject.firstElement()
                .toObservable()
                .switchMap(started -> started
                        ? observable.observeOn(AndroidSchedulers.mainThread()).doOnComplete(onCompleteAction)
                        : Observable.empty())
                .takeUntil(conditionSubject.filter(condition -> condition))
                .subscribe(onNextAction, onErrorAction);
    }

}
