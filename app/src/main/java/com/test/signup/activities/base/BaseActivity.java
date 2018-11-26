package com.test.signup.activities.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.test.signup.TestApp;
import com.test.signup.fragments.navigation.FragmentNavigation;
import com.test.signup.lifecycle.BaseRxLifecycle;
import com.test.signup.lifecycle.RxLifecycle;

import butterknife.ButterKnife;

public abstract class BaseActivity<TView extends MvpView, TPresenter extends MvpPresenter<TView>>
        extends MvpActivity<TView, TPresenter> {

    @NonNull
    protected final BaseRxLifecycle activityRxLifecycle = new BaseRxLifecycle();

    protected boolean firstTimeCreated = true;


    @NonNull
    public abstract FragmentNavigation getNavigation();

    @NonNull
    public RxLifecycle getActivityRxLifecycle() {
        return activityRxLifecycle;
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        onConfigurationNavigation(menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onConfigurationNavigation(@NonNull final Menu menu) {
        // do nothing
    }


    @NonNull
    protected TestApp getList101App() {
        return (TestApp) getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRxLifecycle.onCreate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityRxLifecycle.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        firstTimeCreated = false;
        activityRxLifecycle.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activityRxLifecycle.onDestroy();
    }

}
