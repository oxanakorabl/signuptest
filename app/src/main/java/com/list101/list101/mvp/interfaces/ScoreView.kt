package com.list101.list101.mvp.interfaces

import com.list101.list101.mvp.model.Scores

interface ScoreView : BaseSignUpView {

    fun configureButtons(boostedWithInstagram: Boolean)

    fun displayScore(scores: Scores)

}