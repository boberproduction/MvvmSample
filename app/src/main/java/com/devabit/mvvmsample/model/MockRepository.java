package com.devabit.mvvmsample.model;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;

public class MockRepository implements Repository {

    @Override
    public Flowable<Resource<List<User>>> getUsers() {
        User user = new User("bla");
        Resource<List<User>> resource = Resource.success(Arrays.asList(user));
        return Flowable.just(resource);
    }
}
