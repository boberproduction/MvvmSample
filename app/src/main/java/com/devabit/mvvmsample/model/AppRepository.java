package com.devabit.mvvmsample.model;

import com.devabit.mvvmsample.model.remote.GitHubApi;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AppRepository implements Repository {
    private GitHubApi gitHubApi;

    public AppRepository(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Flowable<Resource<List<User>>> getUsers() {
        return Flowable.create(emitter -> new NetworkBoundResource<List<User>, List<User>>(emitter) {

            @Override
            public Single<List<User>> getRemote() {
                return gitHubApi.getUsersList();
            }

            @Override
            public Flowable<List<User>> getLocal() {
                try (Realm realm = Realm.getDefaultInstance()) {
                    RealmResults<User> result = realm.where(User.class).findAll();

                    List<User> users = realm.copyFromRealm(result);
                    return Flowable.just(users);
                }
            }

            @Override
            public void saveCallResult(List<User> data) {
                try (Realm realm = Realm.getDefaultInstance()) {
                    realm.executeTransaction(realm1 -> {
                        RealmList<User> userList = new RealmList<>();
                        userList.addAll(data);
                        realm1.insert(userList);
                    });
                }
            }

            @Override
            public Function<List<User>, List<User>> mapper() {
                return userList -> userList;
            }

        }, BackpressureStrategy.BUFFER);
    }
}
