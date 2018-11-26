package com.list101.list101.fragments.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.list101.list101.List101App;
import com.list101.list101.activities.base.BaseActivity;
import com.list101.list101.exceptions.UnexpectedException;
import com.list101.list101.lifecycle.BaseRxLifecycle;
import com.list101.list101.lifecycle.RxLifecycle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLifecycleFragment<TView extends MvpView, TPresenter extends MvpPresenter<TView>, TActivity extends BaseActivity>
        extends MvpFragment<TView, TPresenter> {

    @NonNull
    protected final BaseRxLifecycle fragmentRxLifecycle = new BaseRxLifecycle();
    protected boolean firstTimeCreated = true;
    protected boolean firstTimeDestroyedView;
    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getLayoutResource();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View rootFragmentView = inflater.inflate(getLayoutResource(), container, false);
        bindViews(rootFragmentView);
        return rootFragmentView;
    }

    @NonNull
    @Override
    public View getView() {
        if (super.getView() != null) {
            return super.getView();
        } else {
            throw new UnexpectedException("root view of fragment is null, check if you call it after onCreateView()");
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentRxLifecycle.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();

        fragmentRxLifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        firstTimeCreated = false;
        fragmentRxLifecycle.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        firstTimeDestroyedView = true;
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        fragmentRxLifecycle.onDestroy();
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public TActivity getBaseActivity() {
        if (super.getActivity() != null) {
            return (TActivity) super.getActivity();
        } else {
            throw new UnexpectedException("activity is not intended to be null");
        }
    }

    @NonNull
    public RxLifecycle getActivityRxLifecycle() {
        return getBaseActivity().getActivityRxLifecycle();
    }


    @NonNull
    protected List101App getList101App() {
        return (List101App) getActivity().getApplicationContext();
    }

    @NonNull
    protected Object getButterKnifeTarget() {
        return this;
    }

    private void bindViews(@NonNull final View rootFragmentView) {
        unbinder = ButterKnife.bind(getButterKnifeTarget(), rootFragmentView);
    }

}
