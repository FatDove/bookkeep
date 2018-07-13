package com.jia.libutils.RxAndroidUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/* Rxjava封装工具类
 * Created by z2wenfa on 2016/3/30.
 */
public class RxjavaUtil {

    /**
     * 在ui线程中工作
     *
     * @param uiTask
     */
    public static <T> void doInUIThread(UITask<T> uiTask) {
        Observable.just(uiTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UITask<T>>() {
                    @Override
                    public void call(UITask<T> uitask) {
                        uitask.doInUIThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /*** 在IO线程中执行任务 * * @param <T> */
    public static <T> void doInIOThread(IOTask<T> ioTask) {
        Observable.just(ioTask)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<IOTask<T>>() {
                    @Override
                    public void call(IOTask<T> ioTask) {
                        ioTask.doInIOThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 执行Rx通用任务 (IO线程中执行耗时操作 执行完成调用UI线程中的方法) * * @param t * @param <T>
     */
    public static <T> void executeRxTask(CommonRxTask<T> t) {
        MyOnSubscribe<CommonRxTask<T>> onsubscribe = new MyOnSubscribe<CommonRxTask<T>>(t) {
            @Override
            public void call(Subscriber<? super CommonRxTask<T>> subscriber) {
                getT().doInIOThread();
                subscriber.onNext(getT());
                subscriber.onCompleted();
            }
        };
        Observable.create(onsubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommonRxTask<T>>() {
                    @Override
                    public void call(CommonRxTask<T> t) {
                        t.doInUIThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}