package com.devabit.mvvmsample.model;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A generic class that can provide a resource backed by both the local database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <LocalType>
 * @param <RemoteType>
 */
public abstract class NetworkBoundResource<LocalType, RemoteType> {

    public NetworkBoundResource(FlowableEmitter<Resource<LocalType>> emitter) {
        Disposable firstDataDisposable = getLocal()
                .map(Resource::loading)
                .subscribe(emitter::onNext);

        getRemote()
                .map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(localTypeData -> {
                    firstDataDisposable.dispose();
                    saveCallResult(localTypeData);
                    getLocal()
                            .map(Resource::success)
                            .subscribe(emitter::onNext);
                });
    }

    public abstract Single<RemoteType> getRemote();

    public abstract Flowable<LocalType> getLocal();

    public abstract void saveCallResult(LocalType data);

    public abstract Function<RemoteType, LocalType> mapper();

}