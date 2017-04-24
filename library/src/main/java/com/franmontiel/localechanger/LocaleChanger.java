/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.franmontiel.localechanger;

import android.content.Context;
import android.content.res.Configuration;

import com.franmontiel.localechanger.matcher.LanguageMatchingAlgorithm;
import com.franmontiel.localechanger.matcher.MatchingAlgorithm;
import com.franmontiel.localechanger.utils.SystemLocaleRetriever;

import java.util.List;
import java.util.Locale;


public class LocaleChanger {

    private static LocaleChangerDelegate delegate;

    private LocaleChanger() {
    }

    /**
     * Initialize the LocaleChanger, this methods needs to be called before calling any other method of this class and it can only be called once, if not a {@inkn}.
     * <p>
     * If this method was never invoked before it will load the Locale from the supported list which language match with a configured system language,
     * if no match is found the first Locale in the list will be loaded.<p>
     * If this method was invoked before it will load a Locale previously set.
     *
     * @param context
     * @param supportedLocales a list of Locales that your app support
     * @throws IllegalStateException if the LocaleChanger is already initialized
     */
    public static void initialize(Context context, List<Locale> supportedLocales) {
        initialize(context,
                supportedLocales,
                new LanguageMatchingAlgorithm(),
                LocalePreference.PreferSupportedLocale);
    }

    /**
     * Initialize the LocaleChanger, this methods needs to be called before calling any other method this class.<p>
     * If this method was never invoked before it will load a Locale resolved using the provided {@link MatchingAlgorithm} and {@link LocalePreference}.<p>
     * If this method was invoked before it will load a Locale previously set.
     *
     * @param context
     * @param supportedLocales  a list of your app supported locales
     * @param matchingAlgorithm used to find a match between supported and system locales
     * @param preference        used to indicate what Locale it is preferred to load in case of a match
     * @throws IllegalStateException if the LocaleChanger is already initialized
     */
    public static void initialize(Context context,
                                  List<Locale> supportedLocales,
                                  MatchingAlgorithm matchingAlgorithm,
                                  LocalePreference preference) {
        if (delegate != null) {
            throw new IllegalStateException("LocaleChanger is already initialized");
        }

        delegate = new LocaleChangerDelegate(
                new LocalePersistor(context),
                new LocaleResolver(supportedLocales,
                        SystemLocaleRetriever.retrieve(),
                        matchingAlgorithm,
                        preference),
                new AppLocaleChanger(context)
        );

        delegate.initialize();
    }

    /**
     * Clears any Locale set and resolve and load a new default one.
     * This method can be useful if the app implements new supported Locales and it is needed to reload the default one in case there is a best match.
     */
    public static void resetLocale() {
        delegate.resetLocale();
    }

    /**
     * Sets a new default app Locale that will be resolved from the one provided.
     *
     * @param supportedLocale a supported Locale that will be used to resolve the locale to set. The default behavior is to directly load the supported Locale provided
     */
    public static void setLocale(Locale supportedLocale) {
        delegate.setLocale(supportedLocale);
    }

    /**
     * Gets the supported Locale that has been used to set the app Locale.
     *
     * @return
     */
    public static Locale getLocale() {
        return delegate.getLocale();
    }

    /**
     * This method should be used inside {@link android.app.Activity#attachBaseContext(Context)}
     * to create a context configured with the correct Locale.
     *
     * @param context
     * @return the resulting context that should be provided to the super method call.
     */
    public static Context configureBaseContext(Context context) {
        return delegate.configureBaseContext(context);
    }

    /**
     * This method should be called from {@link android.app.Application#onConfigurationChanged(Configuration)}
     */
    public static void onConfigurationChanged() {
        delegate.onConfigurationChanged();
    }
}
