package com.devabit.mvvmsample.userlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.devabit.mvvmsample.App;
import com.devabit.mvvmsample.model.Repository;
import com.devabit.mvvmsample.model.Resource;
import com.devabit.mvvmsample.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class UserListViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private Repository repository;
    private MutableLiveData<List<User>> userList;
    private MutableLiveData<Boolean> progressBarActiveStatus;

    @Inject
    public UserListViewModel(Repository repository) {
        userList = new MutableLiveData<>();
        progressBarActiveStatus = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;
    }

    public MutableLiveData<Boolean> getProgressBarActiveStatus() {
        return progressBarActiveStatus;
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public void refreshButtonClick() {
        userList.setValue(new ArrayList<>());

        compositeDisposable.add(
                repository.getUsers()
                        .subscribeOn(Schedulers.io())
                        .delay(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> progressBarActiveStatus.setValue(true))
                        .unsubscribeOn(Schedulers.io())
                        .subscribeWith(new ResourceSubscriber<Resource<List<User>>>() {
                            @Override
                            public void onNext(Resource<List<User>> listResource) {
                                userList.setValue(listResource.getData());
                                progressBarActiveStatus.setValue(false);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.e(App.APP_TAG, "Error getting users list", t);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
