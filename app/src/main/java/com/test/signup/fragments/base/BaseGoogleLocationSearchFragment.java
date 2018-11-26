package com.test.signup.fragments.base;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fernandocejas.arrow.optional.Optional;
import com.test.signup.R;
import com.test.signup.mvp.interfaces.GoogleLocationView;
import com.test.signup.mvp.model.locations.GoogleLocation;
import com.test.signup.mvp.presenters.GoogleLocationPresenter;
import com.test.signup.ui.recyclerview.DividerItemDecorationLastItems;
import com.test.signup.ui.recyclerview.adapters.GoogleSearchResultsAdapter;
import com.test.signup.utils.LocationUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseGoogleLocationSearchFragment<TResult extends GoogleLocation, TView extends GoogleLocationView<TResult>,
        TPresenter extends GoogleLocationPresenter<TView, TResult>>
        extends BaseMainActivityLifecycleFragment<TView, TPresenter>
        implements GoogleLocationView<TResult> {

    @BindView(R.id.include_google_places_no_result_found_container)
    LinearLayout noResultsFoundContainer;
    @BindView(R.id.include_google_places_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.include_google_places_search_results)
    RecyclerView recyclerView;

    @Nullable
    private Location location;
    @NonNull
    private GoogleSearchResultsAdapter<TResult> googleSearchResultsAdapter;
    @NonNull
    protected final BehaviorSubject<String> currentSearchQueryBehaviorSubject = BehaviorSubject.create();

    @NonNull
    protected abstract Observable<String> getSearchQueryObservable();

    @NonNull
    protected abstract GoogleSearchResultsAdapter.OnItemClickListener<TResult> getItemClickListener();

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerView();
        bindSearchField();
        fragmentRxLifecycle.untilStop(LocationUtils.getLocationWithPermissionsObservable(getBaseActivity())
                        .filter(Optional::isPresent)
                        .map(Optional::get),
                location -> this.location = location,
                throwable -> {
                    // don't need to handle errors in this case
                });
    }

    @Override
    public void showSearchResult(@NonNull final List<TResult> searchResults) {
        recyclerView.setVisibility(View.VISIBLE);
        noResultsFoundContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        googleSearchResultsAdapter.setItems(searchResults);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noResultsFoundContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultsFoundView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noResultsFoundContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        // TODO: implement later
    }

    private void bindSearchField() {
        fragmentRxLifecycle.bind(getSearchQueryObservable()
                        .switchMap(text -> {
                            if (!TextUtils.isEmpty(text)) {
                                return Observable.just(text)
                                        .debounce(750L, TimeUnit.MILLISECONDS);
                            } else {
                                return Observable.just(text);
                            }
                        }),
                text -> {
                    if (location != null) {
                        presenter.performSearchQuery(text, location.getLatitude(), location.getLongitude());
                    } else {
                        presenter.performSearchQuery(text, null, null);
                    }
                });
    }

    private void configureRecyclerView() {
        googleSearchResultsAdapter = new GoogleSearchResultsAdapter<>(fragmentRxLifecycle, currentSearchQueryBehaviorSubject, getItemClickListener());
        recyclerView.setAdapter(googleSearchResultsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        final DividerItemDecorationLastItems dividerItemDecoration = new DividerItemDecorationLastItems(recyclerView.getContext(),
                linearLayoutManager.getOrientation(), 2, (int) getResources().getDimension(R.dimen.item_list_space));

        final Drawable itemDividerDrawable = ContextCompat.getDrawable(getBaseActivity(), android.R.color.transparent);
        dividerItemDecoration.setDrawable(itemDividerDrawable);

        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
