package com.list101.list101.di.signupflow

import com.list101.list101.di.map.signup.SignUpMapComponent
import com.list101.list101.di.map.signup.SignUpMapModule
import com.list101.list101.di.signupflow.scores.ScoreComponent
import com.list101.list101.di.signupflow.scores.ScoreModule
import com.list101.list101.di.signupflow.singup.SignUpComponent
import com.list101.list101.di.signupflow.singup.SignUpModule
import com.list101.list101.fragments.navigation.SignUpStateController
import dagger.Subcomponent

@SignUpFlowScope
@Subcomponent(modules = [SignUpFlowModule::class])
interface SignUpFlowComponent {

    fun plusSignUpComponent(signUpModule: SignUpModule): SignUpComponent

    fun plusScoreComponent(scoreModule: ScoreModule): ScoreComponent

    fun plusSignUpMapComponent(signUpMapModule: SignUpMapModule): SignUpMapComponent

    fun getController(): SignUpStateController

}