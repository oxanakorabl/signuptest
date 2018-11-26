package com.list101.list101.mvp.presenters.signup;

import android.support.annotation.NonNull;

import com.list101.list101.api.apipart.wrappers.SignUpApiWrapper;
import com.list101.list101.api.model.LocationApiModel;
import com.list101.list101.fragments.navigation.SignUpStateController;
import com.list101.list101.lifecycle.RxLifecycle;
import com.list101.list101.mvp.interfaces.MapSelectionView;
import com.list101.list101.mvp.presenters.MapPresenter;

import io.reactivex.Observable;

public class SignUpMapPresenter extends MapPresenter {

    @NonNull
    private final SignUpApiWrapper signUpApiWrapper;
    @NonNull
    private final SignUpStateController controller;

    public SignUpMapPresenter(@NonNull final RxLifecycle rxLifecycle,
                              @NonNull final SignUpApiWrapper signUpApiWrapper,
                              @NonNull final SignUpStateController controller) {
        super(rxLifecycle);

        this.signUpApiWrapper = signUpApiWrapper;
        this.controller = controller;
    }

    @NonNull
    @Override
    public Observable<LocationApiModel> getDecodeLocationObservable(final double latitude, final double longitude) {
        return signUpApiWrapper.getLocationByCoordinates(controller.getSession().getAuthHeader(), latitude, longitude);
    }

    @Override
    public void doOnLocationSelected(@NonNull final LocationApiModel locationApiModel) {
        rxLifecycle.untilStop(signUpApiWrapper.acceptLocation(controller.getSession().getAuthHeader()),
                controller::setNextState,
                throwable -> ifViewAttached(MapSelectionView::showServerError));
    }

    public boolean onBackPressed() {
        return false;
    }

    @NonNull
    public String getHeader() {
        return controller.getSession().getAuthHeader();
    }

}
