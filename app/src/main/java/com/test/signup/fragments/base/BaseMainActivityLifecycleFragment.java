package com.test.signup.fragments.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.test.signup.activities.MainActivity;
import com.test.signup.di.qualifiers.BottomNavigationSubject;
import com.test.signup.fragments.dialog.photo.DialogImageModel;
import com.test.signup.fragments.dialog.photo.FullScreenImagesViewerDialogFragment;
import com.test.signup.fragments.navigation.FragmentNavigation;
import com.test.signup.mvp.interfaces.ShareYourLifeButtonClickListener;
import com.test.signup.ui.controllers.ToolbarInflater;
import com.test.signup.ui.navigation.pager.models.GuideType;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseMainActivityLifecycleFragment<TView extends MvpView, TPresenter extends MvpPresenter<TView>>
        extends BaseLifecycleFragment<TView, TPresenter, MainActivity>
        implements ShareYourLifeButtonClickListener {

    @Inject
    @NonNull
    @BottomNavigationSubject
    protected BehaviorSubject<Boolean> forceBottomNavigationHidingSubject;

    @Nullable
    protected Menu menu;
    @Nullable
    protected MenuItem menuItem;
    @Nullable
    protected MenuInflater menuInflater;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isToolbarScrollable()) {
            getToolbarInflater().setScrollable(false);
        }
        if (isBottomNavigationHidden()) {
            forceBottomNavigationHidingSubject.onNext(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!isToolbarScrollable()) {
            getToolbarInflater().setScrollable(true);
        }
        if (isBottomNavigationHidden()) {
            forceBottomNavigationHidingSubject.onNext(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        onConfigureNavigation(menu, inflater);
    }

    @Override
    public void shareYourLifeButtonClicked() {
        getBaseActivity().getPresenter().onCreatePostClicked();
    }

    @NonNull
    public FragmentNavigation getNavigation() {
        return getBaseActivity().getNavigation();
    }

    @NonNull
    public ToolbarInflater getToolbarInflater() {
        return getBaseActivity().getToolbarInflater();
    }

    public void hideSoftInput() {
        getBaseActivity().hideSoftInput();
    }

    public void showSoftInput(@NonNull final View view) {
        getBaseActivity().showSoftInput(view);
    }

    /**
     * Called when activity configuring ActionBar, Toolbar, Sidebar etc.
     * If it will be called or not, depends on {@link android.support.v4.app.Fragment#hasOptionsMenu()}
     * and {@link android.support.v4.app.Fragment#isMenuVisible()}.
     *
     * @param menu     The options menu in which you place your items;
     * @param inflater Helper to inflate menu items.
     */
    protected void onConfigureNavigation(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        if (isShareYourLifeButtonAdded()) {
            getToolbarInflater().inflateShareYouLifeButton(menu, inflater, item -> {
                shareYourLifeButtonClicked();
                return true;
            });
        }

        this.menu = menu;
        this.menuInflater = inflater;
    }

    protected void openQuickGuide(@GuideType final int guideType) {
        getNavigation().push(QuickGuideFragment.class, QuickGuideFragment.createBundle(guideType, false));
    }

    protected boolean isShareYourLifeButtonAdded() {
        return false;
    }

    protected boolean isToolbarScrollable() {
        return true;
    }

    protected boolean isBottomNavigationHidden() {
        return false;
    }

}
