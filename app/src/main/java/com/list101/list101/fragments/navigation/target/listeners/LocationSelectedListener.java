package com.list101.list101.fragments.navigation.target.listeners;

import android.location.Location;
import android.support.annotation.NonNull;

public interface LocationSelectedListener {

    void setSearchedLocation(@NonNull final Location location);

}
