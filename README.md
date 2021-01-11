LocaleChanger
=================
An Android library to programmatically set the Locale of an app and persist the configuration. 

Download
--------
Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
Step 2. Add the dependency
```groovy
dependencies {
        compile 'com.github.franmontiel:LocaleChanger:1.1'
}
```
Usage
-----
### Basic usage
Initialize the library from your `Application` class with a list of your app supported Locales:
```java
LocaleChanger.initialize(getApplicationContext(), SUPPORTED_LOCALES);
```
The first time that is invoked it will automatically set a Locale taking into account the system configuration. The first element of the supported Locale list will be used as default if no match with the system configured Locales is found.

You also need to call `onConfigurationChange` from the same named method in your `Application` class:
```java
@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    LocaleChanger.onConfigurationChanged();
}
```
And create a new Locale configured `Context` for all your `Activities` overriding the `AppCompatDelegate`:
```java
private LocaleChangerAppCompatDelegate localeChangerAppCompatDelegate;

@NonNull
@Override
public AppCompatDelegate getDelegate() {
    if (localeChangerAppCompatDelegate == null) {
            localeChangerAppCompatDelegate = new LocaleChangerAppCompatDelegate(super.getDelegate());
    }

    return localeChangerAppCompatDelegate;
}
```
To change the Locale just make the following call:
```java
LocaleChanger.setLocale(newLocale);
``` 
### Activity recreation 
You need to recreate the `Activities` once the Locale is changed to reload your resources. You can do it by simply calling the `replace` method of the Activity.

Additionally there is a `ActivityRecreationHelper` class that is intended for assisting you with the recreation of the Activity.
 
It can be used to detect when the Locale has changed and reload automatically the Activity when resumed, for that you must call to the `onResume` and `onDestroy` methods of the helper class from the Activity methods.

In concrete terms:
* On the Activity in which you change the language you need to recreate it calling `ActivityRecreationHelper.recreate`.
* For having the rest of the Activities on the Back Stack recreated automatically when resumed, call `ActivityRecreationHelper.onResume` and `ActivityRecreationHelper.onDestroy` on all your Activities `onResume` and `onDestroy` methods respectively.

### Advanced usage
The default behavior of the library can be changed providing a `MatchingAlgorithm` and a `LocalePreference`
* The `MatchingAlgorithm` is used when the library is initialized and when the Locale is changed to find a match between your supported Locales and the system Locales. One of those matching Locales will be set by the library. There are two classes that implements this interface:
  * `LanguageMatchingAlgorithm` will match the first two Locales with the same language. This is the default algorithm used if no one is defined.
  * `ClosestMatchingAlgorithm` will match the two Locales with most attributes in common (language, country and variation).

* The `LocalePreference` is used to select witch one of the two matching Locales will be set. The default behavior is to prefer a supported locales if no preference is provided.

### Quick configuration (not recommended)
If you want to start really quick and it is not a problem for you to depend on inheritance for this library to work just follow those steps:
* Make all your Activities inherit from `LocaleChangerBaseActivity`
* Make your Application class inherit from `LocaleChangerBaseApplication`


Known issues
-------
* The ActionBar title is not affected by the Locale change if it is defined in the Manifest label attribute. A valid workaround is to set the title programmatically. More info on [Issue #1](https://github.com/franmontiel/LocaleChanger/issues/1).

License
-------
    Copyright (C) 2017 Francisco José Montiel Navarro

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
