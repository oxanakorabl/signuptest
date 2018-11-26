package com.test.signup.mvp.presenters;

import android.support.annotation.NonNull;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.test.signup.lifecycle.RxLifecycle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MvpBaseLifecyclePresenter<TView extends MvpView> extends MvpBasePresenter<TView> {

    @NonNull
    protected final RxLifecycle rxLifecycle;
    @NonNull
    protected final Observable<Boolean> connectionObservable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .replay(1)
            .refCount();

    public MvpBaseLifecyclePresenter(@NonNull final RxLifecycle rxLifecycle) {
        super();

        this.rxLifecycle = rxLifecycle;
    }

    protected final void observeConnection(@NonNull final Consumer<Boolean> consumer) {
        rxLifecycle.bind(connectionObservable, consumer);
    }

}
