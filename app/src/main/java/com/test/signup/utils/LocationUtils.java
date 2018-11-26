package com.test.signup.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.fernandocejas.arrow.optional.Optional;
import com.google.android.gms.maps.model.LatLng;
import com.test.signup.exceptions.PermissionException;
import com.test.signup.storage.dataprovider.OneShotLocationRequester;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

public final class LocationUtils {

    @NonNull
    public static Location getLocationFromLatLng(@NonNull final LatLng latLng) {
        return getLocationFromLatLng(latLng.latitude, latLng.longitude);
    }

    @NonNull
    public static Location getLocationFromLatLng(final double latitude, final double longitude) {
        final Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static boolean isGeoServicesAvailable(@NonNull final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @NonNull
    public static Observable<Optional<Location>> getLocationWithPermissionsObservable(@NonNull final Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.ACCESS_FINE_LOCATION)
                .switchMap(permissionGranted -> {
                    if (permissionGranted) {
                        return OneShotLocationRequester.getLocationObservable(activity)
                                .map(Optional::of);
                    } else {
                        return Observable.error(new PermissionException());
                    }
                });
    }

    public static boolean isLocationTurnedOn(@NonNull final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gpsEnabled || networkEnabled;
    }

    private LocationUtils() {

    }

}
