package pl.olszak.currencies.core.concurrent

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulersProvider {

    fun main(): Scheduler = AndroidSchedulers.mainThread()

    fun io(): Scheduler = Schedulers.io()

    fun computation(): Scheduler = Schedulers.computation()
}
