package com.list101.list101.mvp.interactors;

import android.support.annotation.NonNull;

import com.list101.list101.storage.db.List101Database;
import com.list101.list101.storage.prefs.ReactiveJsonSharedPreferences;
import com.list101.list101.storage.prefs.model.MyProfileApiModel;
import com.list101.list101.storage.prefs.model.PostConfiguration;
import com.list101.list101.storage.prefs.Session;
import com.list101.list101.storage.prefs.model.SkippedQuickGuides;
import com.list101.list101.storage.prefs.model.UploadingPostsList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class SessionInteractor {

    @NonNull
    private final ReactiveJsonSharedPreferences<Session> sessionPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<MyProfileApiModel> profileApiPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<PostConfiguration> postConfigurationPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<UploadingPostsList> uploadingPostsListPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<SkippedQuickGuides> skippedQuickGuidesPreferences;
    @NonNull
    private final List101Database list101Database;

    public SessionInteractor(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences,
                             @NonNull final ReactiveJsonSharedPreferences<MyProfileApiModel> profileApiPreferences,
                             @NonNull final ReactiveJsonSharedPreferences<PostConfiguration> postConfigurationPreferences,
                             @NonNull final ReactiveJsonSharedPreferences<UploadingPostsList> uploadingPostsListPreferences,
                             @NonNull final ReactiveJsonSharedPreferences<SkippedQuickGuides> skippedQuickGuidesPreferences,
                             @NonNull final List101Database list101Database) {
        this.sessionPreferences = sessionPreferences;
        this.profileApiPreferences = profileApiPreferences;
        this.postConfigurationPreferences = postConfigurationPreferences;
        this.uploadingPostsListPreferences = uploadingPostsListPreferences;
        this.skippedQuickGuidesPreferences = skippedQuickGuidesPreferences;
        this.list101Database = list101Database;
    }

    @NonNull
    public Observable<?> getClearSessionObservable() {
        final List<Observable<?>> list = new ArrayList<>();
        list.add(sessionPreferences.set(null).toObservable());
        list.add(profileApiPreferences.set(null).toObservable());
        list.add(postConfigurationPreferences.set(null).toObservable());
        list.add(uploadingPostsListPreferences.set(null).toObservable());
        list.add(skippedQuickGuidesPreferences.set(null).toObservable());
        list.add(list101Database.removeAllDataObservable());
        return Observable.merge(list);
    }

}
