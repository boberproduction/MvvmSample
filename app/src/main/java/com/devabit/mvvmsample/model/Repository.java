package com.devabit.mvvmsample.model;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface Repository {

    Flowable<Resource<List<User>>> getUsers();

}
