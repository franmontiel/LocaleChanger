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

package com.franmontiel.localechanger.matcher;

import com.franmontiel.localechanger.utils.LocaleMatcher;

import java.util.List;
import java.util.Locale;

/**
 * An algorithm that match the Locales the same language in common.
 */
public final class LanguageMatchingAlgorithm implements MatchingAlgorithm {

    @Override
    public MatchingLocales findDefaultMatch(List<Locale> supportedLocales, List<Locale> systemLocales) {
        MatchingLocales matchingPair = null;
        for (Locale systemLocale : systemLocales) {
            Locale matchingSupportedLocale = findMatchingLocale(systemLocale, supportedLocales);
            if (matchingSupportedLocale != null) {
                matchingPair = new MatchingLocales(matchingSupportedLocale, systemLocale);
                break;
            }
        }
        return matchingPair;
    }

    @Override
    public MatchingLocales findMatch(Locale supportedLocale, List<Locale> systemLocales) {
        Locale matchingSystemLocale = findMatchingLocale(supportedLocale, systemLocales);
        return matchingSystemLocale != null ?
                new MatchingLocales(supportedLocale, matchingSystemLocale) :
                null;
    }

    private static Locale findMatchingLocale(Locale localeToMatch, List<Locale> candidates) {
        Locale matchingLocale = null;
        for (Locale candidate : candidates) {
            LocaleMatcher.MatchLevel matchLevel = LocaleMatcher.match(localeToMatch, candidate);

            if (matchLevel != LocaleMatcher.MatchLevel.NoMatch) {
                matchingLocale = candidate;
                break;
            }
        }
        return matchingLocale;
    }
}
