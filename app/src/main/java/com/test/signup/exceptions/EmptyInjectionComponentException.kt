package com.test.signup.exceptions

import kotlin.jvm.JvmOverloads

class EmptyInjectionComponentException @JvmOverloads constructor(componentName: String, componentIsNull: Boolean = true) :
        RuntimeException("Dagger component $componentName is ${if (componentIsNull) "not initialized or already destroyed" else "empty"}")
