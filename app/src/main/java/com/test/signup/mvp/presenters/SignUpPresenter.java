package com.test.signup.mvp.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.login.LoginResult;
import com.test.signup.api.apipart.wrappers.AccountsApiWrapper;
import com.test.signup.api.model.AccessTokenApiModel;
import com.test.signup.api.model.enums.LoginStatus;
import com.test.signup.api.model.response.AuthorizationResponse;
import com.test.signup.fragments.navigation.SignUpStateController;
import com.test.signup.lifecycle.RxLifecycle;
import com.test.signup.mvp.interfaces.SignUpView;
import com.test.signup.storage.prefs.Session;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class SignUpPresenter extends BaseSignUpPresenter<SignUpView> {

    private static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList("email", "public_profile", "user_about_me", "user_website",
            "user_work_history", "user_birthday", "user_education_history", "user_friends", "user_hometown", "user_photos", "user_relationships");

    @NonNull
    private final AccountsApiWrapper accountsApiWrapper;
    @Nullable
    private String authHeader;

    public SignUpPresenter(@NonNull final RxLifecycle rxLifecycle,
                           @NonNull final AccountsApiWrapper accountsApiWrapper,
                           @NonNull final SignUpStateController controller) {
        super(rxLifecycle, controller);

        this.accountsApiWrapper = accountsApiWrapper;
    }

    public void createNewAccount() {
        if (authHeader != null) {
            rxLifecycle.untilStop(accountsApiWrapper.createNewAccount(authHeader)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(ignored -> ifViewAttached(SignUpView::showProgress))
                            .doFinally(() -> ifViewAttached(SignUpView::hideProgress))
                            .doOnTerminate(() -> ifViewAttached(SignUpView::hideProgress)),
                    this::moveToNextStep,
                    throwable -> {
                        Timber.e(throwable);
                        ifViewAttached(view -> view.showError(throwable));
                    });
        }
    }

    public void reactivateAccount() {
        if (authHeader != null) {
            rxLifecycle.untilStop(accountsApiWrapper.reactivateAccount(authHeader)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> ifViewAttached(SignUpView::hideProgress))
                            .doOnTerminate(() -> ifViewAttached(SignUpView::hideProgress)),
                    this::moveToNextStep,
                    throwable -> {
                        Timber.e(throwable);
                        ifViewAttached(view -> view.showError(throwable));
                    });
        }
    }

    @NonNull
    public List<String> getFacebookPermissions() {
        return FACEBOOK_PERMISSIONS;
    }

    public void sendFacebookLoginInfo(@NonNull final LoginResult loginResult) {
        rxLifecycle.untilStop(accountsApiWrapper.sendFacebookLoginInfo(new AccessTokenApiModel(loginResult))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(ignored -> ifViewAttached(SignUpView::showProgress)),
                this::moveToNextStep,
                throwable -> {
                    Timber.e(throwable);
                    ifViewAttached(view -> {
                        view.showError(throwable);
                        view.hideProgress();
                    });
                });
    }

    private void moveToNextStep(@NonNull final AuthorizationResponse response) {
        authHeader = Session.makeAuthHeader(response.getAccessToken());
        ifViewAttached(view -> {
            if (response.getStatus().equals(LoginStatus.DELETED)) {
                view.showReactivationDialog();
            } else {
                getController().setNextState(response);
            }
        });
    }

}