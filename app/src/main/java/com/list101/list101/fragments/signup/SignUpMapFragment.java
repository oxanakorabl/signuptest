package com.list101.list101.fragments.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.list101.list101.List101App;
import com.list101.list101.R;
import com.list101.list101.fragments.base.MapFragment;
import com.list101.list101.mvp.presenters.signup.SignUpMapPresenter;

public class SignUpMapFragment extends MapFragment<SignUpMapPresenter> {

    @Override
    @NonNull
    public SignUpMapPresenter createPresenter() {
        return List101App.getDependenciesScopesController().getSignUpMapFragmentComponent().getMapPresenter();
    }

    @Override
    public boolean onBackPressed() {
        return presenter.onBackPressed();
    }

    @Override
    public void goBack() {
        //Do nothing
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getToolbarInflater().inflateOnlyTitleToolbar(getBaseActivity(), getToolbarTitle());
        getBaseActivity().disableSidebar();
    }

    @Override
    protected void openGoogleLocationSearch() {
        getNavigation().pushForResult(GoogleCityLocationSearchFragment.class, this,
                GoogleCityLocationSearchFragment.getBundle(presenter.getHeader(), getToolbarTitle()));
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.fragment_signup_map_title;
    }

    @Override
    protected int getCityButtonTitle() {
        return R.string.fragment_signup_map_chose_button_title;
    }

    @Override
    protected void onConfigureNavigation(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initMapComponent() {
        List101App.getDependenciesScopesController().createSignUpMapFragmentComponent(fragmentRxLifecycle);
    }

    @Override
    protected void releaseMapComponent() {
        List101App.getDependenciesScopesController().releaseSignUpMapFragmentComponent();
    }

}
