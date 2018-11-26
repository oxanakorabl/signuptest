package com.test.signup.api.model.enums;

import android.support.annotation.StringDef;

@StringDef({LoginStatus.CREATED, LoginStatus.REGISTERED, LoginStatus.LOCATED, LoginStatus.DELETED})
public @interface LoginStatus {

    String CREATED = "Created";
    String REGISTERED = "Registered";
    String LOCATED = "Located";
    String DELETED = "Deleted";

}
