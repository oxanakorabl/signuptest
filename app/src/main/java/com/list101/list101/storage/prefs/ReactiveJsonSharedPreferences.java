package com.list101.list101.storage.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.LoganSquare;
import com.fernandocejas.arrow.optional.Optional;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

// TODO: think about migration when cached model doesn't match POJO model (i.e. model changed but cache still has old one)
public class ReactiveJsonSharedPreferences<T> {

    @NonNull
    private final String key;
    @NonNull
    private final SharedPreferences sharedPreferences;
    @NonNull
    private final PublishSubject<Optional<T>> newValueSubject = PublishSubject.create();
    @NonNull
    private final Observable<Optional<T>> valueObservable;
    @NonNull
    private final Class<T> valueClass;

    public ReactiveJsonSharedPreferences(@NonNull final Context context,
                                         @NonNull final String sharedPreferencesName,
                                         @NonNull final Class<T> valueClass,
                                         @NonNull final String key) {
        super();

        sharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        this.valueClass = valueClass;
        this.key = key;
        valueObservable = createValueObservable();
    }

    @NonNull
    public Observable<Optional<T>> observe() {
        return valueObservable;
    }

    @NonNull
    public Observable<Optional<T>> getAsync() {
        return valueObservable.first(Optional.absent()).toObservable();
    }

    /**
     * Gets value synchronously. You should NOT use this method normally. Use {@link #getAsync()} or {@link #observe()} asynchronously instead.
     */
    @NonNull
    public Optional<T> getSync() {
        return valueObservable.blockingFirst();
    }

    /**
     * Creates completable which is async setting value to store.
     *
     * @param newValue Value to set;
     * @return {@link io.reactivex.Completable} of setting process.
     */
    @NonNull
    public Completable set(@Nullable final T newValue) {
        return valueObservable
                .firstElement()
                .flatMapCompletable(valueOptional -> valueOptional.isPresent() && valueOptional.get().equals(newValue)
                        ? Completable.complete()
                        : Completable.create(
                        subscriber -> {
                            saveObject(newValue);
                            Timber.i("Value of '%s' changed from '%s' to '%s'",
                                    key, !valueOptional.isPresent() ? null : valueOptional.get(), newValue);
                            newValueSubject.onNext(Optional.fromNullable(newValue));
                            subscriber.onComplete();
                        }))
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    private Observable<Optional<T>> createValueObservable() {
        final Observable<Optional<T>> result = Observable
                .<Optional<T>>create(subscriber -> {
                    subscriber.onNext(Optional.fromNullable(getObject(key)));
                    subscriber.onComplete();
                })
                .concatWith(newValueSubject)
                .subscribeOn(Schedulers.io());
        return result
                .replay(1)
                .refCount();
    }

    private void saveObject(@Nullable final T value) throws IOException {
        if (value == null) {
            sharedPreferences.edit().remove(key).apply();
            return;
        }
        sharedPreferences.edit().putString(key, transformObjectToJson(value)).apply();
    }

    @Nullable
    private T getObject(@NonNull final String key) throws IOException {
        if (!contains(key)) {
            return null;
        }

        final String json = sharedPreferences.getString(key, null);
        return transformJsonToObject(json);
    }

    private boolean contains(@NonNull final String key) {
        return sharedPreferences.contains(key);
    }

    @NonNull
    private String transformObjectToJson(@NonNull final T value) throws IOException {
        return LoganSquare.serialize(value);
    }

    @Nullable
    private T transformJsonToObject(@Nullable final String json) throws IOException {
        if (json == null) {
            return null;
        }
        return LoganSquare.parse(json, valueClass);
    }

}
