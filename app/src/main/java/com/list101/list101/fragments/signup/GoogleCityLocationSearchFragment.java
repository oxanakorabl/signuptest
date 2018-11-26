package com.list101.list101.fragments.signup;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.list101.list101.List101App;
import com.list101.list101.R;
import com.list101.list101.fragments.base.BaseGoogleLocationSearchFragment;
import com.list101.list101.fragments.navigation.target.listeners.LocationSelectedListener;
import com.list101.list101.mvp.interfaces.GoogleLocationView;
import com.list101.list101.mvp.model.locations.GoogleLocationWithCoordinates;
import com.list101.list101.mvp.model.locations.LocationCoordinates;
import com.list101.list101.mvp.presenters.GoogleCityLocationPresenter;
import com.list101.list101.ui.recyclerview.adapters.GoogleSearchResultsAdapter;
import com.list101.list101.utils.LocationUtils;
import com.list101.list101.utils.SearchHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class GoogleCityLocationSearchFragment extends BaseGoogleLocationSearchFragment<GoogleLocationWithCoordinates,
        GoogleLocationView<GoogleLocationWithCoordinates>, GoogleCityLocationPresenter>
        implements GoogleLocationView<GoogleLocationWithCoordinates> {

    private static final String EMPTY_STRING = "";

    private static final String SIGN_UP_AUTH_HEADER_ARG = "SIGN_UP_AUTH_HEADER_ARG";
    private static final String TITLE_ID = "TITLE_ID";

    @NonNull
    public static Bundle getBundle(@Nullable final String signUpAuthHeader,
                                   @StringRes final int titleId) {
        final Bundle bundle = new Bundle();
        bundle.putString(SIGN_UP_AUTH_HEADER_ARG, signUpAuthHeader);
        bundle.putInt(TITLE_ID, titleId);
        return bundle;
    }

    @BindView(R.id.fragment_google_city_location_search_edit_text)
    EditText searchEditText;
    @BindView(R.id.fragment_google_city_location_search_image_view_cross)
    ImageView crossImageView;

    @Nullable
    private String signUpAuthHeader;
    @StringRes
    private int titleId;
    @NonNull
    private Observable<String> searchQueryObservable;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        injectActivityComponent();

        super.onCreate(savedInstanceState);

        titleId = getArguments().getInt(TITLE_ID);
        signUpAuthHeader = getArguments().getString(SIGN_UP_AUTH_HEADER_ARG);
    }

    @Override
    @NonNull
    public GoogleCityLocationPresenter createPresenter() {
        return new GoogleCityLocationPresenter(fragmentRxLifecycle, signUpAuthHeader);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        searchQueryObservable = SearchHelper.createTextChangeObservable(searchEditText, currentSearchQueryBehaviorSubject);
        super.onViewCreated(view, savedInstanceState);

        getToolbarInflater().inflateTitleToolbarWithBothIcons(getBaseActivity(), titleId);

        handleEmptyQuery(searchQueryObservable);
    }

    @Override
    public void onStart() {
        super.onStart();

        showSoftInput(searchEditText);
    }

    @Override
    public void onStop() {
        super.onStop();

        hideSoftInput();
    }

    @Override
    protected boolean isBottomNavigationHidden() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_google_city_location_search;
    }

    @Override
    @NonNull
    protected Observable<String> getSearchQueryObservable() {
        return searchQueryObservable;
    }

    @NonNull
    @Override
    protected GoogleSearchResultsAdapter.OnItemClickListener<GoogleLocationWithCoordinates> getItemClickListener() {
        return locationSearchResult -> {
            final LocationCoordinates locationCoordinates = locationSearchResult.getLocationCoordinates();
            final Location location = LocationUtils.getLocationFromLatLng(locationCoordinates.getLatitude(), locationCoordinates.getLongitude());
            ((LocationSelectedListener) getTargetFragment()).setSearchedLocation(location);
            getNavigation().back();
        };
    }

    @OnClick(R.id.fragment_google_city_location_search_image_view_cross)
    void clickOnSearchCross() {
        searchEditText.setText(EMPTY_STRING);
    }

    private void handleEmptyQuery(@NonNull final Observable<String> searchObservable) {
        fragmentRxLifecycle.bind(searchObservable
                        .distinctUntilChanged(),
                query -> {
                    if (TextUtils.isEmpty(query)) {
                        crossImageView.setVisibility(View.GONE);
                        showSearchResult(new ArrayList<>());
                    } else {
                        crossImageView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void injectActivityComponent() {
        List101App.getDependenciesScopesController().getActivityComponent().inject(this);
    }

}
