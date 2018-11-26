package com.test.signup.di.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.test.signup.BuildConfig;
import com.test.signup.api.apipart.wrappers.ImagesApiWrapper;
import com.test.signup.api.model.EndpointApiModel;
import com.test.signup.di.qualifiers.LoggedIn;
import com.test.signup.di.qualifiers.LoggedInUserId;
import com.test.signup.di.qualifiers.MainEndpointPreferences;
import com.test.signup.di.qualifiers.ProfileImagesUploadInteractor;
import com.test.signup.di.qualifiers.SignalrEndpointPreferences;
import com.test.signup.exceptions.UnexpectedException;
import com.test.signup.mvp.interactors.GuideInteractor;
import com.test.signup.mvp.interactors.SessionInteractor;
import com.test.signup.mvp.interactors.StartUploadInteractor;
import com.test.signup.services.post.UploadPostPrefsInteractor;
import com.test.signup.storage.db.List101Database;
import com.test.signup.storage.db.model.chat.Models;
import com.test.signup.storage.prefs.ReactiveJsonSharedPreferences;
import com.test.signup.storage.prefs.model.MyProfileApiModel;
import com.test.signup.storage.prefs.model.PostConfiguration;
import com.test.signup.storage.prefs.Session;
import com.test.signup.storage.prefs.model.SkippedQuickGuides;
import com.test.signup.storage.prefs.model.UploadPostItemsList;
import com.test.signup.storage.prefs.model.UploadingPostsList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.reactivex.subjects.BehaviorSubject;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.KotlinReactiveEntityStore;
import io.requery.sql.KotlinEntityDataStore;
import io.requery.sql.TableCreationMode;

@Module
@SuppressWarnings("PMD.TooManyMethods")
public class AppModule {

    private static final String PROFILE_KEY = "PROFILE_KEY";
    private static final String SESSION_KEY = "SESSION_KEY";
    private static final String POST_CONFIGURATION_KEY = "POST_CONFIGURATION_KEY";
    private static final String UPLOADING_POST_HELPER_LIST_KEY = "UPLOADING_POST_HELPER_LIST_KEY";
    private static final String UPLOADING_POSTS_LIST_KEY = "UPLOADING_POSTS_LIST_KEY";
    private static final String SKIPPED_QUICK_GUIDES_KEY = "SKIPPED_QUICK_GUIDES_KEY";
    private static final String ENDPOINT_KEY = "ENDPOINT_KEY";
    private static final String SIGNALR_ENDPOINT_KEY = "SIGNALR_ENDPOINT_KEY";
    private static final String APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES";
    private static final String LIST_101_DB_NAME = "list101";

    @NonNull
    private final Context appContext;
    @NonNull
    private final ReactiveJsonSharedPreferences<Session> sessionPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<PostConfiguration> postConfigurationPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<UploadingPostsList> uploadingPostsListPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<UploadPostItemsList> postHelperListPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<SkippedQuickGuides> skippedQuickGuidesPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<MyProfileApiModel> myProfileApiModelPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<EndpointApiModel> endpointPreferences;
    @NonNull
    private final ReactiveJsonSharedPreferences<EndpointApiModel> signalrEndpointPreferences;
    // TODO REMOVE_BEFORE_RELEASE
    @NonNull
    private final BehaviorSubject<Integer> postErrorBehaviorSubject = BehaviorSubject.create();
    @NonNull
    private final BehaviorSubject<BadgePayload> badgeCountBehaviourSubject = BehaviorSubject.create();

    public AppModule(@NonNull final Context context) {
        appContext = context;
        sessionPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, Session.class, SESSION_KEY);
        postConfigurationPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, PostConfiguration.class,
                POST_CONFIGURATION_KEY);
        uploadingPostsListPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, UploadingPostsList.class,
                UPLOADING_POSTS_LIST_KEY);
        skippedQuickGuidesPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, SkippedQuickGuides.class,
                SKIPPED_QUICK_GUIDES_KEY);
        postHelperListPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, UploadPostItemsList.class,
                UPLOADING_POST_HELPER_LIST_KEY);
        myProfileApiModelPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, MyProfileApiModel.class, PROFILE_KEY);
        endpointPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, EndpointApiModel.class,
                ENDPOINT_KEY);
        signalrEndpointPreferences = new ReactiveJsonSharedPreferences<>(appContext, APP_SHARED_PREFERENCES, EndpointApiModel.class,
                SIGNALR_ENDPOINT_KEY);
    }

    @NonNull
    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }

    @NonNull
    @Provides
    @Singleton
    BehaviorSubject<Integer> providePostErrorBehaviorSubject() {
        // TODO REMOVE_BEFORE_RELEASE
        return postErrorBehaviorSubject;
    }

    @NonNull
    @Provides
    @Singleton
    BehaviorSubject<BadgePayload> provideBadgeCountBehaviourSubject() {
        return badgeCountBehaviourSubject;
    }

    @NonNull
    @Provides
    @Singleton
    ReactiveJsonSharedPreferences<Session> provideSessionSharedPreferences() {
        return sessionPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    ReactiveJsonSharedPreferences<PostConfiguration> providePostConfigurationSharedPreferences() {
        return postConfigurationPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    ReactiveJsonSharedPreferences<UploadingPostsList> provideUploadingPostsListPreferences() {
        return uploadingPostsListPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    ReactiveJsonSharedPreferences<SkippedQuickGuides> provideSkippedQuickGuidesPreferences() {
        return skippedQuickGuidesPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    ReactiveJsonSharedPreferences<MyProfileApiModel> provideProfileSharedPreferences() {
        return myProfileApiModelPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    @MainEndpointPreferences
    ReactiveJsonSharedPreferences<EndpointApiModel> provideEndpointPreferences() {
        return endpointPreferences;
    }

    @NonNull
    @Provides
    @Singleton
    @SignalrEndpointPreferences
    ReactiveJsonSharedPreferences<EndpointApiModel> provideSignalrEndpointPreferences() {
        return signalrEndpointPreferences;
    }

    @Provides
    @LoggedIn
    boolean provideIsLoggedIn(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences) {
        return sessionPreferences.getSync().isPresent() && sessionPreferences.getSync().get().isLoggedIn();
    }

    @NonNull
    @Provides
    Session provideSession(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences) {
        if (sessionPreferences.getSync().isPresent()) {
            return sessionPreferences.getSync().get();
        } else {
            throw new UnexpectedException("Provide session when you logged in");
        }
    }

    @NonNull
    @Provides
    @LoggedInUserId
    public String provideLoggedInUserId(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences) {
        return sessionPreferences.getSync().get().getNonNullUserId();
    }

    // TODO: think about StartUploadInteractor, where it should be and where it should be used
    @NonNull
    @Provides
    @ProfileImagesUploadInteractor
    @Singleton
    StartUploadInteractor provideUploadProfileImagesInteractor(@NonNull final Context context,
                                                               @NonNull final ImagesApiWrapper imagesApiWrapper) {
        return new StartUploadInteractor(context, imagesApiWrapper);
    }

    // TODO: think about StartUploadInteractor, where it should be and where it should be used
    @NonNull
    @Provides
    StartUploadInteractor provideUploadInteractor(@NonNull final Context context,
                                                  @NonNull final ImagesApiWrapper imagesApiWrapper) {
        return new StartUploadInteractor(context, imagesApiWrapper);
    }

    @NonNull
    @Provides
    @Singleton
    UploadPostPrefsInteractor provideUploadPostPrefsInteractor() {
        return new UploadPostPrefsInteractor(postHelperListPreferences, uploadingPostsListPreferences);
    }

    @NonNull
    @Provides
    @Reusable
    GuideInteractor provideGuideInteractor() {
        return new GuideInteractor(skippedQuickGuidesPreferences, sessionPreferences);
    }

    @NonNull
    @Provides
    @Singleton
    ShortcutBadgerController provideShortcutBadgerController(@NonNull final Context context) {
        return new ShortcutBadgerController(context);
    }

    @NonNull
    @Provides
    @Singleton
    SessionInteractor provideSessionInteractor(@NonNull final ReactiveJsonSharedPreferences<Session> sessionPreferences,
                                               @NonNull final ReactiveJsonSharedPreferences<MyProfileApiModel> profileApiPreferences,
                                               @NonNull final ReactiveJsonSharedPreferences<PostConfiguration> postConfigurationPreferences,
                                               @NonNull final ReactiveJsonSharedPreferences<UploadingPostsList> uploadingPostsListPreferences,
                                               @NonNull final ReactiveJsonSharedPreferences<SkippedQuickGuides> skippedQuickGuidesPreferences,
                                               @NonNull final List101Database list101Database) {
        return new SessionInteractor(sessionPreferences, profileApiPreferences, postConfigurationPreferences, uploadingPostsListPreferences,
                skippedQuickGuidesPreferences, list101Database);
    }

    @NonNull
    @Provides
    @Singleton
    KotlinReactiveEntityStore<Persistable> provideEntityStore() {
        final DatabaseSource databaseSource = new DatabaseSource(appContext, Models.DEFAULT, LIST_101_DB_NAME, 1);
        if (BuildConfig.DEBUG) {
            databaseSource.setLoggingEnabled(true);
        }
        databaseSource.setTableCreationMode(TableCreationMode.CREATE_NOT_EXISTS);
        return new KotlinReactiveEntityStore<>(new KotlinEntityDataStore<Persistable>(databaseSource.getConfiguration()));
    }

    @NonNull
    @Provides
    @Singleton
    List101Database provideList101Database(@NonNull final KotlinReactiveEntityStore<Persistable> kotlinReactiveEntityStore) {
        return new List101Database(kotlinReactiveEntityStore);
    }

}
