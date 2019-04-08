package com.codingexercise.flickr.util.rx;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppRxSchedulers implements RxSchedulers {

    public static Scheduler BACKGROUND_SCHEDULERS = Schedulers.from(Executors.newCachedThreadPool());
    public static Scheduler INTERNET_SCHEDULERS = Schedulers.from(Executors.newCachedThreadPool());

    @Override
    public Scheduler runOnBackground() {
        return BACKGROUND_SCHEDULERS;
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler androidThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler internet() {
        return INTERNET_SCHEDULERS;
    }
}
