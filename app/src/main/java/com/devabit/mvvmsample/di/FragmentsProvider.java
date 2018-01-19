package com.devabit.mvvmsample.di;

import com.devabit.mvvmsample.userlist.UserListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentsProvider {

    @ContributesAndroidInjector(modules = UserListFragmentModule.class)
    abstract UserListFragment provideUserListFragment();
}
