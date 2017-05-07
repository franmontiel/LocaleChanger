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
 * An algorithm that matches the Locales with most attributes in common.
 */
public final class ClosestMatchingAlgorithm implements MatchingAlgorithm {

    @Override
    public MatchingLocales findDefaultMatch(List<Locale> supportedLocales, List<Locale> systemLocales) {
        MatchingLocales bestMatchingLocalePair = null;
        MatchingLocales languageAndCountryMatchingLocalePair = null;
        MatchingLocales languageMatchingLocalePair = null;

        for (Locale systemLocale : systemLocales) {
            for (Locale supportedLocale : supportedLocales) {

                LocaleMatcher.MatchLevel match = LocaleMatcher.match(systemLocale, supportedLocale);

                if (match.equals(LocaleMatcher.MatchLevel.CompleteMatch)) {
                    bestMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);
                    break;

                } else if (match.equals(LocaleMatcher.MatchLevel.LanguageAndCountryMatch)
                        && languageAndCountryMatchingLocalePair == null) {
                    languageAndCountryMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);

                } else if (match.equals(LocaleMatcher.MatchLevel.LanguageMatch)
                        && languageMatchingLocalePair == null) {
                    languageMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);
                }
            }
            if (bestMatchingLocalePair != null)
                break;
        }

        if (bestMatchingLocalePair != null) {
            return bestMatchingLocalePair;
        } else if (languageAndCountryMatchingLocalePair != null) {
            return languageAndCountryMatchingLocalePair;
        } else {
            return languageMatchingLocalePair;
        }
    }

    @Override
    public MatchingLocales findMatch(Locale supportedLocale, List<Locale> systemLocales) {
        MatchingLocales bestMatchingLocalePair = null;
        MatchingLocales languageAndCountryMatchingLocalePair = null;
        MatchingLocales languageMatchingLocalePair = null;

        for (Locale systemLocale : systemLocales) {
            LocaleMatcher.MatchLevel match = LocaleMatcher.match(systemLocale, supportedLocale);

            if (match.equals(LocaleMatcher.MatchLevel.CompleteMatch)) {
                bestMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);
                break;

            } else if (match.equals(LocaleMatcher.MatchLevel.LanguageAndCountryMatch)
                    && languageAndCountryMatchingLocalePair == null) {
                languageAndCountryMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);

            } else if (match.equals(LocaleMatcher.MatchLevel.LanguageMatch)
                    && languageMatchingLocalePair == null) {
                languageMatchingLocalePair = new MatchingLocales(supportedLocale, systemLocale);
            }
        }

        if (bestMatchingLocalePair != null) {
            return bestMatchingLocalePair;
        } else if (languageAndCountryMatchingLocalePair != null) {
            return languageAndCountryMatchingLocalePair;
        } else {
            return languageMatchingLocalePair;
        }
    }
}