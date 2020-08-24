# OMDB Movie Search
Search movies from omdb, loads list and detail

![image Demo](demo.gif)

## UI ##

* Android ViewModel, Live Data, View State wrapped content delivered to UI
* Recycler View
* Android Navigation components for storyboard and easy navigation

![image Navigation](navigation.png)

## API ##

* Retrofit and OkHttp Client, gson
* Coroutines


## Data ##

* Repository pattern used to separate data retrieval.
* Viewmodel is used to comebine data and state to pass to view.

## Requirements ##
* Targets Android P and above
* Requires emulator above 29

## TODO ##

* Dependency Injection using Hilt/Koin
* Switch over to Flutter to allow iOS app
* Clean up styles, and extract strings into constants
* Fix up Unit tests to work with livedata, coroutines, and viewmodel


