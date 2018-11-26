package com.list101.list101.mvp.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.api.model.LocationApiModel;
import com.list101.list101.lifecycle.RxLifecycle;
import com.list101.list101.mvp.interfaces.MapSelectionView;
import com.list101.list101.mvp.presenters.base.MvpBaseLifecyclePresenter;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public abstract class MapPresenter extends MvpBaseLifecyclePresenter<MapSelectionView> {

    @Nullable
    private Disposable decodeDisposable;

    public MapPresenter(@NonNull final RxLifecycle rxLifecycle) {
        super(rxLifecycle);

        observeConnection(connected -> ifViewAttached(view -> {
            if (connected) {
                view.reloadViewIfNeeded();
            }
        }));
    }

    @NonNull
    protected abstract Observable<LocationApiModel> getDecodeLocationObservable(final double latitude, final double longitude);

    protected abstract void doOnLocationSelected(@NonNull final LocationApiModel locationApiModel);

    public void decodeLocation(final double latitude, final double longitude) {
        if (decodeDisposable != null && !decodeDisposable.isDisposed()) {
            decodeDisposable.dispose();
        }
        decodeDisposable = rxLifecycle.untilStop(getDecodeLocationObservable(latitude, longitude),
                locationApiModel ->
                        ifViewAttached(view -> {
                            if (locationApiModel.isAcceptable() && !locationApiModel.getLocation().equals("")) {
                                view.showChooseCityButton();
                                view.setLocation(locationApiModel);
                                return;
                            }
                            view.hideChooseCityButton();
                        }), throwable ->
                        ifViewAttached(view -> {
                            view.showServerError();
                            view.hideChooseCityButton();
                        }));
    }

    public void processSelectedLocation(@NonNull final LocationApiModel locationApiModel) {
        rxLifecycle.untilStop(connectionObservable,
                connected ->
                        ifViewAttached(view -> {
                                    if (connected) {
                                        doOnLocationSelected(locationApiModel);
                                    } else {
                                        view.animateConnectionErrorRibbon();
                                    }
                                }
                        ));
    }

}
