package com.test.signup.mvp.presenters

import com.test.signup.api.apipart.wrappers.SignUpApiWrapper
import com.test.signup.fragments.navigation.SignUpStateController
import com.test.signup.lifecycle.RxLifecycle
import com.test.signup.mvp.interfaces.ScoreView
import com.test.signup.mvp.model.Scores
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class ScorePresenter(rxLifecycle: RxLifecycle,
                     private val signUpApiWrapper: SignUpApiWrapper,
                     controller: SignUpStateController) : BaseSignUpPresenter<ScoreView>(rxLifecycle, controller) {

    var scores: Scores? = null

    fun getScore() {
        if (scores == null) {
            rxLifecycle.untilStop(signUpApiWrapper.getScores(controller.session.authHeader)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { ifViewAttached(ScoreView::showProgress) }
                    .doFinally { ifViewAttached(ScoreView::hideProgress) }
                    .doOnTerminate { ifViewAttached(ScoreView::hideProgress) },
                    { scoresResponse ->
                        scores = Scores(scoresResponse)
                        scores?.let {
                            ifViewAttached { view -> view.displayScore(it) }
                        }
                    },
                    { throwable ->
                        ifViewAttached { view -> view.showError(throwable) }
                        Timber.e(throwable)
                    })
        }
    }

    fun acceptScore() {
        rxLifecycle.untilStop(signUpApiWrapper.acceptScores(controller.session.authHeader)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { ifViewAttached(ScoreView::showProgress) },
                { response -> controller.setNextState(response) },
                { throwable ->
                    ifViewAttached { view ->
                        view.showError(throwable)
                        view.hideProgress()
                    }
                    Timber.e(throwable)
                })
    }

}


