package com.test.signup.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.test.signup.TestApp;
import com.test.signup.R;
import com.test.signup.activities.base.BaseActivity;
import com.test.signup.fragments.navigation.FragmentNavigation;
import com.test.signup.mvp.interfaces.MainActivityView;
import com.test.signup.mvp.presenters.MainActivityPresenter;

public class MainActivity extends BaseActivity<MainActivityView, MainActivityPresenter> implements MainActivityView {

    private FragmentNavigation navigation;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        navigation = new FragmentNavigation(this, getSupportFragmentManager(), R.id.activity_main_content_container);
        initActivityComponent();

        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public FragmentNavigation getNavigation() {
        return navigation;
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return TestApp.getDependenciesScopesController().getActivityComponent().getMainActivityPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firstTimeCreated) {
            presenter.setFirstScreenOnFirstLaunch();
        }
    }

    @Override
    protected void onDestroy() {
        releaseActivityComponent();

        super.onDestroy();

    }

    private void initActivityComponent() {
        TestApp.getDependenciesScopesController().createActivityComponent(this, activityRxLifecycle, getNavigation()).inject(this);
    }

    private void releaseActivityComponent() {
        TestApp.getDependenciesScopesController().releaseActivityComponent();
    }

}
