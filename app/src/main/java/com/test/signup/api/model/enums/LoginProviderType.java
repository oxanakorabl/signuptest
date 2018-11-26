package com.test.signup.api.model.enums;

import android.support.annotation.StringDef;

@StringDef({LoginProviderType.FACEBOOK})
public @interface LoginProviderType {

    // TODO: instagram will be added later
    String FACEBOOK = "Facebook";

}
