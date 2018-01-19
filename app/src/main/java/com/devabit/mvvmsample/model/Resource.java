package com.devabit.mvvmsample.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.devabit.mvvmsample.model.Resource.Status.ERROR;
import static com.devabit.mvvmsample.model.Resource.Status.LOADING;
import static com.devabit.mvvmsample.model.Resource.Status.SUCCESS;

/**
 * Generic class that describes data with a status.
 */
public class Resource<T> {
    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final String message;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public enum Status {
        SUCCESS, ERROR, LOADING
    }
}