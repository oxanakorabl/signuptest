package com.test.signup.fragments.navigation

import com.test.signup.fragments.signup.ScoreFragment
import com.test.signup.fragments.signup.SignUpFragment
import com.test.signup.fragments.signup.SignUpMapFragment

class Router(private val fragmentNavigation: FragmentNavigation) {

    fun openSignUp() {
        fragmentNavigation.setInitial(SignUpFragment::class.java)
    }

    fun openSignUpMap() {
        fragmentNavigation.push(SignUpMapFragment::class.java)
    }

    fun openSignUpScore() {
        fragmentNavigation.push(ScoreFragment::class.java)
    }

}