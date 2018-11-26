package com.test.signup.mvp.presenters;

import android.support.annotation.NonNull;

import com.test.signup.api.apipart.wrappers.SignUpApiWrapper;
import com.test.signup.api.model.LocationApiModel;
import com.test.signup.fragments.navigation.SignUpStateController;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.interfaces.MapSelectionView;

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
