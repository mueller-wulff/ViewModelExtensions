/*
 * Copyright (c) 2018.  Müller & Wulff GmbH. All rights reserved.
 */

package com.muellerwulff.viewmodelextensions

import android.app.Application
import android.arch.lifecycle.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by Hans Markwart on 16.03.2018
 */

object ViewModels {

    inline fun <reified T : ViewModel> of(
        fragment: Fragment,
        noinline factory: ((modelClass: Class<*>) -> T)? = null
    ): T =
        if (factory == null) {
            ViewModelProviders.of(fragment)[T::class.java]
        } else {
            ViewModelProviders.of(
                fragment,
                FunctionalViewModelFactory(factory)
            )[T::class.java]
        }

    inline fun <reified T : ViewModel> of(
        activity: FragmentActivity,
        noinline factory: ((modelClass: Class<*>) -> T)? = null
    ): T =
        if (factory == null) {
            ViewModelProviders.of(activity)[T::class.java]
        } else {
            ViewModelProviders.of(
                activity,
                FunctionalViewModelFactory(factory)
            )[T::class.java]
        }

    class FunctionalViewModelFactory<VM>(
        private val factory: (modelClass: Class<*>) -> VM
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return factory(modelClass) as? T
                ?: error("cannot create ${modelClass.simpleName} from supplied factory")
        }
    }
}

//view model creation

inline fun <reified T : ViewModel> Fragment.viewModel(noinline factory: ((modelClass: Class<*>) -> T)? = null): T =
    ViewModels.of(this, factory)

inline fun <reified T : ViewModel> FragmentActivity.viewModel(noinline factory: ((modelClass: Class<*>) -> T)? = null): T =
    ViewModels.of(this, factory)

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

fun <T> liveDataOf(default: T? = null) = MutableLiveData<T>().apply { value = default }

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> LiveData<T>.requireValue(): T = value ?: error("no value given")