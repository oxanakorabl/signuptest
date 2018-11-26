package com.list101.list101.mvp.interfaces;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.list101.list101.mvp.model.locations.GoogleLocation;

import java.util.List;

public interface GoogleLocationView<T extends GoogleLocation> extends MvpView {

    void showSearchResult(@NonNull final List<T> searchResults);

    void showProgressBar();

    void hideProgressBar();

    void showNoResultsFoundView();

    void showError();

}
