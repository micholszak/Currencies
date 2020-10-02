package pl.olszak.currencies.core.concurrent

import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class MapSchedulerFacadeTest {

    companion object {
        private val OBSERVABLES: List<Observable<Int>> = listOf(
            Observable.just(5),
            Observable.empty(),
            Observable.fromIterable(listOf(5, 6, 7, 8))
        )

        private fun getObservableSources(listSize: Int): List<Observable<Int>> =
            List(size = listSize) {
                OBSERVABLES.random()
            }
    }

    private val testSchedulersProvider = TestSchedulersProvider()
    private val mapSchedulerFacade = MapSchedulerFacade(testSchedulersProvider)

    @Test
    fun `Add subscription to facade on subscribe`() {
        val subscriber = "tag"
        subscribeUsingFacade(10, subscriber)

        val disposables = mapSchedulerFacade.disposableFor(subscriber)
        assertThat(disposables.size()).isEqualTo(10)
    }

    @Test
    fun `Remove all subscriptions on unsubscribe`() {
        val subscriber = "something"
        subscribeUsingFacade(5, subscriber)

        mapSchedulerFacade.unsubscribeFor(subscriber)
        val disposables = mapSchedulerFacade.disposableFor(subscriber)
        assertThat(disposables.size()).isEqualTo(0)
    }

    @Test
    fun `Remove subscriptions only for chosen subscriber`() {
        val firstSubscriber = "first one"
        subscribeUsingFacade(5, firstSubscriber)
        val secondSubscriber = "second"
        subscribeUsingFacade(10, secondSubscriber)

        mapSchedulerFacade.unsubscribeFor(firstSubscriber)
        val firstDisposable = mapSchedulerFacade.disposableFor(firstSubscriber)
        assertThat(firstDisposable.size()).isEqualTo(0)

        val secondDisposable = mapSchedulerFacade.disposableFor(secondSubscriber)
        assertThat(secondDisposable.size()).isEqualTo(10)
    }

    private fun subscribeUsingFacade(subscriptionsCount: Int, subscriber: Any) {
        val sources = getObservableSources(subscriptionsCount)
        sources.forEach { source ->
            mapSchedulerFacade.subscribe(
                subscriber = subscriber,
                source = source
            )
        }
    }
}
