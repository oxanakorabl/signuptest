package com.list101.list101.fragments.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernandocejas.arrow.optional.Optional;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.list101.list101.R;
import com.list101.list101.activities.SystemNavigation;
import com.list101.list101.activities.base.BaseActivity;
import com.list101.list101.api.model.LocationApiModel;
import com.list101.list101.exceptions.ConnectionException;
import com.list101.list101.exceptions.GeoServicesUnavailableException;
import com.list101.list101.exceptions.PermissionException;
import com.list101.list101.exceptions.UnexpectedException;
import com.list101.list101.fragments.navigation.target.listeners.LocationSelectedListener;
import com.list101.list101.mvp.interfaces.MapSelectionView;
import com.list101.list101.mvp.presenters.MapPresenter;
import com.list101.list101.utils.LocationUtils;
import com.list101.list101.utils.SnackbarUtils;

import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.TooManyFields", "PMD.ExcessiveImports"})
// TODO: PMD - extract map logic in a separate object
public abstract class MapFragment<TPresenter extends MapPresenter> extends BaseMainActivityLifecycleFragment<MapSelectionView, TPresenter>
        implements MapSelectionView, OnMapReadyCallback, LocationSelectedListener, BaseActivity.OnBackPressedListener {

    public static final float DEFAULT_CURRENT_LOCATION_ZOOM = 10.0f;
    public static final float DEFAULT_LOCATION_ZOOM = 3.5f;

    @NonNull
    private static TranslateAnimation getTranslateAnimationForSignMoreView() {
        final TranslateAnimation anim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, -0.2f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.2f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f);
        anim.setDuration(500L);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        return anim;
    }

    @BindView(R.id.fragment_map_map_view)
    MapView mapView;
    @BindView(R.id.fragment_map_choose_city_button)
    View chooseCityButton;
    @BindView(R.id.fragment_map_city_name)
    TextView cityNameTextView;
    @BindView(R.id.fragment_map_image_view_sign_more)
    ImageView signMoreView;
    @BindView(R.id.fragment_map_choose_city_title)
    TextView chooseCityButtonTitle;

    @NonNull
    private final LatLng defaultLatLng = new LatLng(51.1788999187949f, -1.8263999372720f);
    @Nullable
    private LocationApiModel selectedLocationApiModel;
    @Nullable
    private Location selectedSearchedLocation;
    private Location defaultLocation;
    private boolean viewDestroying;
    @NonNull
    private final BehaviorSubject<Boolean> locationRequestedSubject = BehaviorSubject.createDefault(true);
    private TranslateAnimation anim;
    @NonNull
    private final BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(@NonNull final Context context, @NonNull final Intent intent) {
            if (intent.getAction().equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                dismissLocationSnackbarIfLocationEnabled();
            }
        }
    };

    @Nullable
    private Snackbar locationSnackbar;
    @Nullable
    private Snackbar permissionSnackbar;

    @StringRes
    protected abstract int getToolbarTitle();

    @StringRes
    protected abstract int getCityButtonTitle();

    protected abstract void initMapComponent();

    protected abstract void releaseMapComponent();

    protected abstract void openGoogleLocationSearch();

    @Override
    public void setSearchedLocation(@NonNull final Location searchedLocation) {
        selectedSearchedLocation = searchedLocation;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        initMapComponent();

        super.onCreate(savedInstanceState);

        defaultLocation = LocationUtils.getLocationFromLatLng(defaultLatLng);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        anim = getTranslateAnimationForSignMoreView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getToolbarInflater().inflateTitleToolbarWithBurger(getBaseActivity(), getToolbarTitle());
        initLocationSnackbar();
        initPermissionSnackbar();
        chooseCityButtonTitle.setText(getCityButtonTitle());
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        moveCamera(googleMap, defaultLatLng);
        fragmentRxLifecycle.untilDestroy(locationRequestedSubject
                        .filter(locationRequested -> locationRequested)
                        .switchMap(locationRequested -> {
                            if (selectedSearchedLocation != null) {
                                return Observable.just(Optional.of(selectedSearchedLocation));
                            }
                            if (LocationUtils.isLocationTurnedOn(getBaseActivity())) {
                                return LocationUtils.getLocationWithPermissionsObservable(getBaseActivity())
                                        .onErrorResumeNext(swallowErrorsAndReturnDefaultLocationObservable());
                            } else {
                                showLocationSettingsSnackbar();
                                return Observable.just(Optional.of(defaultLocation));
                            }
                        })
                        .filter(Optional::isPresent)
                        .map(Optional::get),
                location -> moveCamera(googleMap, new LatLng(location.getLatitude(), location.getLongitude())));
        addLocationListeners(googleMap);
    }

    @Override
    public void onStart() {
        super.onStart();

        getBaseActivity().addOnBackPressedListener(this);
        viewDestroying = false;
        mapView.onStart();
        dismissLocationSnackbarIfLocationEnabled();
        getBaseActivity().registerReceiver(locationReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();

        mapView.onStop();
        getBaseActivity().removeOnBackPressedListener(this);
        getBaseActivity().unregisterReceiver(locationReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        viewDestroying = true;
        mapView.onDestroy();

        dismissLocationSnackbar();
        dismissPermissionSnackbar();
        super.onDestroyView();

    }

    @Override
    public void onSaveInstanceState(@Nullable final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (mapView != null) {
            mapView.onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        releaseMapComponent();

        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void setLocation(@NonNull final LocationApiModel locationApiModel) {
        selectedLocationApiModel = locationApiModel;
        cityNameTextView.setText(locationApiModel.getLocation());
    }

    @Override
    public void showChooseCityButton() {
        chooseCityButton.setVisibility(View.VISIBLE);
        signMoreView.startAnimation(anim);
    }

    @Override
    public void hideChooseCityButton() {
        cityNameTextView.setText(null);
        chooseCityButton.setVisibility(View.INVISIBLE);
        signMoreView.clearAnimation();
    }

    @Override
    public void showServerError() {
        getBaseActivity().showError(R.string.server_error);
    }

    @Override
    public void animateConnectionErrorRibbon() {
        getBaseActivity().animateInternetConnectionAbsence();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_map;
    }

    @OnClick(R.id.fragment_map_city_name_container)
    void pushGoogleLocationSearch() {
        selectedSearchedLocation = null;
        openGoogleLocationSearch();
    }

    @OnClick(R.id.fragment_map_current_location_button)
    void showCurrentLocation() {
        if (LocationUtils.isLocationTurnedOn(getBaseActivity())) {
            locationRequestedSubject.onNext(true);
        } else {
            showLocationSettingsSnackbar();
        }
    }

    @OnClick(R.id.fragment_map_choose_city_button)
    void chooseLocation() {
        if (selectedLocationApiModel == null) {
            throw new UnexpectedException("something went wrong, selectedLocationApiModel can not be null when you push chooseLocation button");
        }
        presenter.processSelectedLocation(selectedLocationApiModel);
    }

    @NonNull
    private Function<Throwable, ObservableSource<Optional<Location>>> swallowErrorsAndReturnDefaultLocationObservable() {
        return throwable -> {
            if (throwable instanceof PermissionException) {
                showPermissionSettingsSnackbar();
                return Observable.just(Optional.absent());
            } else if (throwable instanceof ConnectionException
                    || throwable instanceof GeoServicesUnavailableException
                    || throwable instanceof TimeoutException) {
                return Observable.just(Optional.absent());
            }
            return Observable.just(Optional.of(defaultLocation));
        };
    }

    private void moveCamera(@NonNull final GoogleMap googleMap, @NonNull final LatLng latLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                latLng.equals(defaultLatLng) ? DEFAULT_LOCATION_ZOOM : DEFAULT_CURRENT_LOCATION_ZOOM));
    }

    private void showLocationSettingsSnackbar() {
        if (locationSnackbar != null) {
            locationSnackbar.show();
        }
    }

    private void dismissLocationSnackbarIfLocationEnabled() {
        if (LocationUtils.isLocationTurnedOn(getBaseActivity()) && locationSnackbar != null && locationSnackbar.isShown()) {
            locationSnackbar.dismiss();
            initLocationSnackbar();
        }
    }

    private void dismissLocationSnackbar() {
        if (locationSnackbar != null && locationSnackbar.isShown()) {
            locationSnackbar.dismiss();
        }
    }

    private void dismissPermissionSnackbar() {
        if (permissionSnackbar != null && permissionSnackbar.isShown()) {
            permissionSnackbar.dismiss();
        }
    }

    private void initLocationSnackbar() {
        final View.OnClickListener actionClickListener = ignored -> SystemNavigation.showLocationSettings(getBaseActivity());
        final Action dismissAction = () -> {
            if (!viewDestroying) {
                initLocationSnackbar();
            }
        };
        locationSnackbar = SnackbarUtils.getSettingSnakcbar(getView(), R.string.fragment_map_turn_on_location, Snackbar.LENGTH_INDEFINITE,
                actionClickListener, dismissAction);
    }

    private void initPermissionSnackbar() {
        final View.OnClickListener actionClickListener = ignored -> SystemNavigation.showSettings(getBaseActivity());
        final Action dismissAction = () -> {
            if (!viewDestroying) {
                initPermissionSnackbar();
            }
        };
        permissionSnackbar = SnackbarUtils.getSettingSnakcbar(getView(), R.string.fragment_map_turn_on_location_permission, Snackbar.LENGTH_LONG,
                actionClickListener, dismissAction);
    }

    private void showPermissionSettingsSnackbar() {
        if (permissionSnackbar != null) {
            permissionSnackbar.show();
        }
    }

    private void addLocationListeners(@NonNull final GoogleMap googleMap) {
        googleMap.setOnCameraIdleListener(() -> {
            final LatLng position = googleMap.getCameraPosition().target;
            presenter.decodeLocation(position.latitude, position.longitude);
        });

        googleMap.setOnCameraMoveStartedListener(gesture -> {
            if (gesture == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                cityNameTextView.setText(null);
                chooseCityButton.setVisibility(View.INVISIBLE);
                signMoreView.clearAnimation();
            }
        });
    }

}
