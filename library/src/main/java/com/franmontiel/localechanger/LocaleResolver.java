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

import com.franmontiel.localechanger.matcher.MatchingAlgorithm;
import com.franmontiel.localechanger.matcher.MatchingLocales;

import java.util.List;
import java.util.Locale;

/**
 * Class that uses a {@link MatchingAlgorithm} and a {@link LocalePreference} to resolve a Locale to be set.
 */
class LocaleResolver {

    private List<Locale> supportedLocales;
    private List<Locale> systemLocales;
    private MatchingAlgorithm matchingAlgorithm;
    private LocalePreference preference;

    LocaleResolver(List<Locale> supportedLocales,
                   List<Locale> systemLocales,
                   MatchingAlgorithm matchingAlgorithm,
                   LocalePreference preference) {

        this.supportedLocales = supportedLocales;
        this.systemLocales = systemLocales;
        this.matchingAlgorithm = matchingAlgorithm;
        this.preference = preference;
    }

    DefaultResolvedLocalePair resolveDefault() {

        MatchingLocales matchingPair = matchingAlgorithm.findDefaultMatch(supportedLocales, systemLocales);

        return matchingPair != null ?
                new DefaultResolvedLocalePair(matchingPair.getSupportedLocale(), matchingPair.getPreferredLocale(preference)) :
                new DefaultResolvedLocalePair(supportedLocales.get(0), supportedLocales.get(0));
    }

    Locale resolve(Locale supportedLocale) throws UnsupportedLocaleException {
        if (!supportedLocales.contains(supportedLocale))
            throw new UnsupportedLocaleException();

        MatchingLocales matchingPair = null;
        if (preference.equals(LocalePreference.PreferSystemLocale)) {
            matchingPair = matchingAlgorithm.findMatch(supportedLocale, systemLocales);
        }

        return matchingPair != null ?
                matchingPair.getPreferredLocale(preference) :
                supportedLocale;
    }
}