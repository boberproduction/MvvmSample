package com.devabit.mvvmsample.di;

import com.devabit.mvvmsample.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            FragmentsProvider.class })
    abstract MainActivity bindMainActivity();
}

