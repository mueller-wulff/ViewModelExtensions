/*
 * Copyright (c) 2018.  Müller & Wulff GmbH. All rights reserved.
 */

package com.muellerwulff.viewmodelextensions

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*

/**
 * Created by Hans Markwart on 16.03.2018
 */


@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModelsCustom(
    noinline viewModelProducer: (() -> VM)
) = viewModels<VM> { SpecificFactory.create(viewModelProducer) }

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModelsCustom(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline viewModelProducer: (() -> VM)
) = viewModels<VM>(
    { ownerProducer() },
    { SpecificFactory.create(viewModelProducer) })

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModelsCustom(
    noinline viewModelProducer: (() -> VM)
) = activityViewModels<VM> { SpecificFactory.create(viewModelProducer) }


@Suppress("UNCHECKED_CAST")
class SpecificFactory<VM : ViewModel>(
    private val producerClass: Class<VM>,
    private val producer: () -> VM
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (producerClass != modelClass) error("this factory can only create instances of $producerClass, $producerClass is not supported")
        return producer() as T
    }

    companion object {
        inline fun <reified VM : ViewModel> create(noinline producer: () -> VM) =
            SpecificFactory(VM::class.java, producer)
    }
}

//view model

val AndroidViewModel.application: Application
    get() = getApplication()

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    this.observe(owner, Observer { observer(it) })
}

fun <T> LiveData<T>.observeRequired(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer {
        if (it == null) return@Observer
        observer(it)
    })
}

fun <T> mutableLiveDataOf(default: T? = null) = MutableLiveData<T>(default)

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> LiveData<T>.requireValue(): T = value ?: error("no value given")

fun <X, Y> LiveData<X>.map(transform: (X) -> Y): LiveData<Y> =
    Transformations.map(this, transform)

fun <X, Y> LiveData<X>.switchMap(transform: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this, transform)