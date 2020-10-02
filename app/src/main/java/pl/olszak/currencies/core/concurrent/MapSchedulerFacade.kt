package pl.olszak.currencies.core.concurrent

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapSchedulerFacade @Inject constructor(
    private val schedulersProvider: SchedulersProvider
) : SchedulerFacade {

    private val subscriptions: MutableMap<String, CompositeDisposable> = mutableMapOf()

    override fun <Data> subscribe(
        subscriber: Any,
        source: Observable<Data>,
        onNext: (Data) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val subscription = source
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.main())
            .subscribe(onNext, onError, onComplete)
        addSubscription(subscriber, subscription)
    }

    override fun unsubscribeFor(subscriber: Any) {
        val disposable = disposableFor(subscriber)
        disposable.clear()
        subscriptions.remove(subscriber.toString())
    }

    private fun addSubscription(subscriber: Any, subscription: Disposable) {
        val disposable = disposableFor(subscriber)
        disposable.add(subscription)
    }

    fun disposableFor(subscriber: Any): CompositeDisposable =
        subscriptions[subscriber.toString()] ?: CompositeDisposable().also {
            subscriptions[subscriber.toString()] = it
        }
}
