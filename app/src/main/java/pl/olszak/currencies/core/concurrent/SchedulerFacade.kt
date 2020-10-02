package pl.olszak.currencies.core.concurrent

import io.reactivex.rxjava3.core.Observable

interface SchedulerFacade {

    fun <Data> subscribe(
        subscriber: Any,
        source: Observable<Data>,
        onNext: (Data) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
    )

    fun unsubscribeFor(subscriber: Any)
}
