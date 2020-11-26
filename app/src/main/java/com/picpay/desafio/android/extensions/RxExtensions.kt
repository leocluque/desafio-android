package com.picpay.desafio.android.extensions

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

fun <T> Single<T>.singleSubscribe(
    onSuccess: ((t: T) -> Unit)? = null,
    onError: ((e: Throwable) -> Unit)? = null,
    observeOnScheduler: Scheduler? = AndroidSchedulers.mainThread()
) =
    this.observeOn(observeOnScheduler)
        .subscribeWith(object : DisposableSingleObserver<T>() {
            override fun onSuccess(t: T) {
                onSuccess?.let { it(t) }
            }

            override fun onError(e: Throwable) {
                onError?.let { it(e) }
            }
        })