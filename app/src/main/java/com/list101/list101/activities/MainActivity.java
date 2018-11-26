package com.list101.list101.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.list101.list101.List101App;
import com.list101.list101.R;
import com.list101.list101.activities.base.BaseActivity;
import com.list101.list101.fragments.navigation.FragmentNavigation;
import com.list101.list101.mvp.interfaces.MainActivityView;
import com.list101.list101.mvp.presenters.MainActivityPresenter;

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
        return List101App.getDependenciesScopesController().getActivityComponent().getMainActivityPresenter();
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
        List101App.getDependenciesScopesController().createActivityComponent(this, activityRxLifecycle, getNavigation()).inject(this);
    }

    private void releaseActivityComponent() {
        List101App.getDependenciesScopesController().releaseActivityComponent();
    }

}
