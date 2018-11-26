package com.list101.list101.mvp.presenters;

import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.list101.list101.List101App;
import com.list101.list101.api.apipart.wrappers.AccountsApiWrapper;
import com.list101.list101.di.qualifiers.LoggedIn;
import com.list101.list101.di.signupflow.SignUpFlowComponent;
import com.list101.list101.fragments.navigation.Router;
import com.list101.list101.lifecycle.RxLifecycle;
import com.list101.list101.mvp.interactors.SessionInteractor;
import com.list101.list101.mvp.interfaces.MainActivityView;
import com.list101.list101.mvp.presenters.base.MvpBaseLifecyclePresenter;
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences;
import com.list101.list101.storage.prefs.Session;

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
        final SignUpFlowComponent component = List101App.getDependenciesScopesController().createSignUpFlowComponent(rxLifecycle);
        component.getController().startLogin();
    }

    private void injectActivityComponent() {
        List101App.getDependenciesScopesController().getActivityComponent().inject(this);
    }

}
