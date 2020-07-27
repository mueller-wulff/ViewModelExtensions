package com.muellerwulff.viewmodelextensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * todo: add readme with link https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its repeated use.
     */
    fun getContentIfNotHandled(): T? = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

/**
 * Wraps [this] into an [Event]
 */
fun <T> T.asEvent() = Event(this)

/**
 * Observes on [this], only emits unhandled [Event] instances to [onNewEvent]
 */
fun <T, E : Event<T>?> LiveData<E>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    onNewEvent: (T) -> Unit
) = this.observe(lifecycleOwner) {
    it?.getContentIfNotHandled()?.let(onNewEvent)
}

/**
 * Observes forever on [this], only emits unhandled [Event] instances to [onNewEvent]
 */
fun <T, E : Event<T>?> LiveData<E>.observeEventForever(onNewEvent: (T) -> Unit) =
    this.observeForever {
        it?.getContentIfNotHandled()?.let(onNewEvent)
    }