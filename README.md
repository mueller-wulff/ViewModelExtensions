# View Model Extensions

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

instead you can use easy to use extension method inside a `FragmentActivity` or `Fragment`:

```kotlin
val model: ViewModel = viewModel()
```

or when you want to use a custom `ViewModel` with a non-empty constructor:

```kotlin
val model: ViewModel = viewModel { ViewModel("a string", 12) }
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
implementation 'com.github.sihamark:ViewModelExtensions:1.0'
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
