package com.devabit.mvvmsample.di;

import android.app.Application;
import android.content.Context;

import com.devabit.mvvmsample.model.AppRepository;
import com.devabit.mvvmsample.model.remote.GitHubApi;
import com.devabit.mvvmsample.model.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Repository providesRepository(GitHubApi gitHubApi) {
        return new AppRepository(gitHubApi);
    }

    @Provides
    @Singleton
    GitHubApi providesGitHubApi() {
        return new Retrofit.Builder()
                .baseUrl(GitHubApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApi.class);
    }

}
