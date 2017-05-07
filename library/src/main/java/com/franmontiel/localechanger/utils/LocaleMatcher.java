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

package com.franmontiel.localechanger.utils;

import java.util.Locale;

import static com.franmontiel.localechanger.utils.LocaleMatcher.MatchLevel.NoMatch;

/**
 * Helper class useful to determine the level of matching of two Locales.
 */
public class LocaleMatcher {

    /**
     * Enum representing the level of matching of two Locales.
     */
    public enum MatchLevel {
        NoMatch,
        LanguageMatch,
        LanguageAndCountryMatch,
        CompleteMatch
    }

    private LocaleMatcher() {
    }

    /**
     * Method to determine the level of matching of two Locales.
     * @param l1
     * @param l2
     * @return
     */
    public static MatchLevel match(Locale l1, Locale l2) {
        MatchLevel matchLevel = NoMatch;
        if (l1.equals(l2)) {
            matchLevel = MatchLevel.CompleteMatch;
        } else if (l1.getLanguage().equals(l2.getLanguage()) && l1.getCountry().equals(l2.getCountry())) {
            return MatchLevel.LanguageAndCountryMatch;
        } else if (l1.getLanguage().equals(l2.getLanguage())) {
            return MatchLevel.LanguageMatch;
        }
        return matchLevel;
    }
}
