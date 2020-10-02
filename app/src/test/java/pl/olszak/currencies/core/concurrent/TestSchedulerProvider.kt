package pl.olszak.currencies.core.concurrent

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulerProvider(
    private val computation: Scheduler = Schedulers.trampoline(),
    private val io: Scheduler = Schedulers.trampoline(),
    private val main: Scheduler = Schedulers.trampoline()
) : SchedulersProvider {

    override fun computation(): Scheduler = computation

    override fun io(): Scheduler = io

    override fun main(): Scheduler = main
}
