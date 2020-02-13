# View Model Extensions

[![](https://jitpack.io/v/mueller-wulff/ViewModelExtensions.svg)](https://jitpack.io/#mueller-wulff/ViewModelExtensions)

This is a small collection of helper classes/methods to simplify the usage of `ViewModel` in kotlin projects.

Usage
-----

When getting a `ViewModel` from `ViewModelProviders` you generally have to write:

```kotlin
ViewModelProviders.of(fragment)[T::class.java]
```

and when you want to have a custom `ViewModelProvider.Factory` it gets even more convoluted:

```kotlin
ViewModelProviders.of(fragment, object: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //create ViewModel based on the supplied modelClass
    }
})[T::class.java]
```

instead you can use easy-to-use property delegate inside a `ComponentActivity` or `Fragment`:

```kotlin
val model: MyViewModel by viewModels()
```

or when you want to use a custom `ViewModel` with a non-empty constructor:

```kotlin
val model: MyViewModel by viewModelsCustom { MyViewModel("a string", 12) }
```

Migrating from version 1.7.0 to 1.8.0
-------------------------------------

Some functions have been removed, because they are available in the androidx ktx packages.
Those functions are:
- `LiveData.observe`
- `LiveData.observeRequired`
- `LiveData.map`
- `LiveData.switchMap`

All those functions are available by using

```gradle
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
```

Events
------

When wanting to publish events as live data, there is a problem when multiple observers register to 
the same `LiveData`, because normal `LiveData` does not regulate consuming of events. 

There is a solution presenting by Jose Alcérreca on Medium
(find it [here](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150))
, where a new Event class is introduced to regulate consuming of events.

This `Event` class is now available as part of this library, simply define a `LiveData` of an `Event`:

```kotlin
private val _warning = mutableLiveDataOf<Event<String>>()
val warning = _warning.asLiveData()
```

and then you can consume the event in the activity or fragment of you choice:

```kotlin
model.warning.observe(this) { event ->
    event?.getContentIfNotHandled()?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}
```

this will guarantee that the event is only consumed once.

Utility Methods
---------------

To create a `MutableLiveData` object you can use the `mutableLiveDataOf` function. 
You can turn any `MutableLiveData` into a `LiveData` using the `asLiveData` function:

```kotlin
private val _number = mutableLiveDataOf(5)
val number = _number.asLiveData()
```

When using a `AndroidViewModel` there is now an extension property to access the `application` 
without using the getter.

When wanting to transform `LiveData` with [Transformations](https://developer.android.com/reference/android/arch/lifecycle/Transformations)
you can now use an extension method like:

```kotlin
private val _number = liveDataOf(5)
val number = _number.asLiveData()

val numberMultitude = number.map { it * 25 }
```

Installation
------------

add jitpack to repositories.

top-level build.gradle
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

add ViewModelExtensions to application build.gradle
```groovy
implementation 'com.github.mueller-wulff:ViewModelExtensions:1.8.0'
```

License
-------

    Copyright 2018 Hans Markwart

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
