package com.list101.list101.fragments.navigation

import com.list101.list101.List101App
import com.list101.list101.api.model.enums.LoginStatus
import com.list101.list101.api.model.response.AuthorizationResponse
import com.list101.list101.exceptions.UnexpectedException
import com.list101.list101.fragments.navigation.SignUpStateController.SignUpState.ENTRY
import com.list101.list101.fragments.navigation.SignUpStateController.SignUpState.LOGGED_IN
import com.list101.list101.fragments.navigation.SignUpStateController.SignUpState.MAP
import com.list101.list101.fragments.navigation.SignUpStateController.SignUpState.SCORE
import com.list101.list101.lifecycle.EmptyAction
import com.list101.list101.lifecycle.RxLifecycle
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences
import com.list101.list101.storage.prefs.Session

class SignUpStateController(private val activityRxLifecycle: RxLifecycle,
                            private val router: Router,
                            private val sessionPreferences: ReactiveJsonSharedPreferences<Session>) {

    var session: Session = Session()
    var state: SignUpState? = null

    fun startLogin() {
        setEntryState()
    }

    fun setNextState(response: AuthorizationResponse) {
        when (state) {
            ENTRY -> choseNextState(response)
            MAP -> {
                setHeader(response)
                setScoreState()
            }
            SCORE -> {
                setHeader(response)
                setLoggedInState()
            }
            LOGGED_IN -> throw UnexpectedException("Can't move to next state")
        }
    }

    private fun choseNextState(response: AuthorizationResponse) {
        setSession(response)
        when (response.status) {
            LoginStatus.REGISTERED -> setLoggedInState()
            LoginStatus.CREATED -> setMapState()
            LoginStatus.LOCATED -> setScoreState()
            else -> throw UnexpectedException("Can't move to next state")
        }
    }

    fun onBackPressed() = when (state) {
        ENTRY -> false
        MAP, SCORE -> {
            setEntryState()
            true
        }
        else -> throw UnexpectedException("We couldn't go back from " + state)
    }

    private fun setSession(response: AuthorizationResponse) {
        session = Session(response.accessToken, response.userId, response.status == LoginStatus.REGISTERED)
    }

    private fun setHeader(response: AuthorizationResponse) {
        session = Session(response.accessToken, session.userId, response.status == LoginStatus.REGISTERED)
    }

    private fun setEntryState() {
        state = ENTRY
        router.openSignUp()
    }

    private fun setMapState() {
        state = MAP
        router.openSignUpMap()
    }

    private fun setScoreState() {
        state = SCORE
        router.openSignUpScore()
    }

    private fun setLoggedInState() {
        activityRxLifecycle.bind(sessionPreferences.set(session)
                .doOnComplete{ List101App.getDependenciesScopesController().releaseSignUpFlowComponent() }
                .toObservable(), EmptyAction<Any>())
    }

    enum class SignUpState {
        ENTRY, MAP, SCORE, LOGGED_IN
    }

}