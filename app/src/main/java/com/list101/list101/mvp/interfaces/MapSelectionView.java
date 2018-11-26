package com.list101.list101.mvp.interfaces;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.MapView;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.list101.list101.api.model.LocationApiModel;

public interface MapSelectionView extends MvpView {

    void setLocation(@NonNull LocationApiModel locationApiModel);

    void showServerError();

    void animateConnectionErrorRibbon();

    void showChooseCityButton();

    void hideChooseCityButton();

    void goBack();

}
