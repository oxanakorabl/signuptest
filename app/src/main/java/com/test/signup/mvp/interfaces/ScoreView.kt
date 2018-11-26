package com.test.signup.mvp.interfaces

import com.test.signup.mvp.model.Scores

interface ScoreView : BaseSignUpView {

    fun configureButtons(boostedWithInstagram: Boolean)

    fun displayScore(scores: Scores)

}