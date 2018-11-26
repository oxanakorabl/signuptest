package com.list101.list101.mvp.presenters.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.api.apipart.wrappers.GeoApiWrapper;
import com.list101.list101.lifecycle.RxLifecycle;
import com.list101.list101.mvp.interfaces.GoogleLocationView;
import com.list101.list101.mvp.model.locations.GoogleLocation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public abstract class GoogleLocationPresenter<TView extends GoogleLocationView<TResult>, TResult extends GoogleLocation>
        extends MvpBaseLifecyclePresenter<TView> {

    @NonNull
    @Inject
    protected GeoApiWrapper geoApiWrapper;

    @Nullable
    private Disposable searchDisposable;

    public GoogleLocationPresenter(@NonNull final RxLifecycle rxLifecycle) {
        super(rxLifecycle);
    }

    @NonNull
    protected abstract Observable<List<TResult>> getSearchObservable(@NonNull final String query,
                                                                     @Nullable final Double latitude,
                                                                     @Nullable final Double longitude);

    public void performSearchQuery(@NonNull final String query, @Nullable final Double latitude, @Nullable final Double longitude) {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        if (!query.isEmpty()) {
            searchDisposable = rxLifecycle.untilStop(getSearchObservable(query, latitude, longitude)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(ignored -> {
                                if (isViewAttached()) {
                                    getView().showProgressBar();
                                }
                            })
                            .doOnNext(ignored -> hideProgressBar())
                            .doFinally(this::hideProgressBar),
                    searchList -> {
                        if (isViewAttached()) {
                            if (!searchList.isEmpty()) {
                                getView().showSearchResult(searchList);
                            } else {
                                getView().showNoResultsFoundView();
                            }
                        }
                    }, throwable -> {
                        if (isViewAttached()) {
                            getView().showError();
                        }
                    });
        } else if (isViewAttached()) {
            getView().showSearchResult(new ArrayList<>());
        }
    }

    private void hideProgressBar() {
        if (isViewAttached()) {
            getView().hideProgressBar();
        }
    }

}
