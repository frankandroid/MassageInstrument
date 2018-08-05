package com.frank.massageinstrument.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by huliuda on 17-6-20.
 */

public class RxUtil {
    public static Subscription doDelay(long delay, Action1<Object> action1) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS).subscribe(action1);
    }

    public static Subscription doDelayInMainThread(long delay, Action1<Object> action1) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

    public static Subscription doInMainThread(Action1<Object> action1) {
        return Observable.just(null).subscribeOn(AndroidSchedulers.mainThread()).subscribe(action1);
    }

    public static Subscription doDelayInMainThread(long delay, Action1<Long> action1, Action1<Throwable> throwableAction1) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1,throwableAction1);
    }

    public static Subscription getTimer(long initialDelay, long period, TimeUnit unit,
                                        Action1<Long> action1, Action1<Throwable> action2) {
        return Observable.interval(initialDelay, period, unit)
                .subscribe(action1,action2);
    }

    public static Subscription getTimerInMainThread(long initialDelay, long period, TimeUnit unit,
                                                    Action1<Long> action1, Action1<Throwable> throwableAction1) {
        return Observable.interval(initialDelay, period, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1,throwableAction1);
    }


}
