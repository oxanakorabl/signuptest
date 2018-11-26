package com.test.signup.mvp.presenters;

import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.test.signup.TestApp;
import com.test.signup.api.apipart.wrappers.AccountsApiWrapper;
import com.test.signup.di.qualifiers.LoggedIn;
import com.test.signup.di.signupflow.SignUpFlowComponent;
import com.test.signup.fragments.navigation.Router;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.interactors.SessionInteractor;
import com.test.signup.mvp.interfaces.MainActivityView;
import com.test.signup.storage.prefs.ReactiveJsonSharedPreferences;
import com.test.signup.storage.prefs.Session;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MainActivityPresenter extends MvpBaseLifecyclePresenter<MainActivityView> {

    @Inject
    @LoggedIn
    boolean loggedIn;
    @Inject
    @NonNull
    AccountsApiWrapper accountsApiWrapper;
    @Inject
    @NonNull
    ReactiveJsonSharedPreferences<Session> sessionPreferences;
    @Inject
    @NonNull
    Router router;
    @Inject
    @NonNull
    SessionInteractor sessionInteractor;


    public MainActivityPresenter(@NonNull final RxLifecycle rxLifecycle) {
        super(rxLifecycle);

        injectActivityComponent();
        observeSessionChanges();
    }


    public void setFirstScreenOnFirstLaunch() {
        setFirstScreen(loggedIn);
    }

    private void observeSessionChanges() {
        rxLifecycle.bind(sessionPreferences.observe()
                        .skip(1)
                        .map(sessionOptional -> sessionOptional.isPresent() && sessionOptional.get().isLoggedIn())
                        .switchMap(loggedIn -> {
                            // подписаться и отобразить  бейджи\ нотификации на иконке приложения и bottomBar
                            return Observable.just(loggedIn);
                        }),
                loggedIn -> ifViewAttached(view -> {
                    if (loggedIn) {
                        //view.hideUnreadNotifications();
                    } else {
                        LoginManager.getInstance().logOut();
                    }
                    setFirstScreen(loggedIn);
                    //removeIconBadges();
                }));
    }

    private void setFirstScreen(final boolean loggedIn) {
        if (loggedIn) {
            // sideMenuItemSelectedSubject.onNext(SideMenuItem.OFFICIAL_LIST);
            //  bottomNavigationItemSelectedSubject.onNext(BottomNavigationItem.OFFICIAL_LIST);
        } else {
            login();
        }
    }

    private void login() {
        final SignUpFlowComponent component = TestApp.getDependenciesScopesController().createSignUpFlowComponent(rxLifecycle);
        component.getController().startLogin();
    }

    private void injectActivityComponent() {
        TestApp.getDependenciesScopesController().getActivityComponent().inject(this);
    }

}
