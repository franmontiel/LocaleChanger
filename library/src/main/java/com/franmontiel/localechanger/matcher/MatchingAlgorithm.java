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

import java.util.List;
import java.util.Locale;

/**
 * Matching algorithm that is used by the library.
 */
public interface MatchingAlgorithm {

    /**
     * Method that implements the algorithm to find two matching Locales between a list of supported and system Locales.
     *
     * @param supportedLocales a list of your app supported locales
     * @param systemLocales    a list of the configured  locales in system preferences
     * @return a {@link MatchingLocales} containing the pair of matching locales. If no match is found null is returned
     */
    MatchingLocales findDefaultMatch(List<Locale> supportedLocales, List<Locale> systemLocales);

    /**
     * Method that implements the algorithm to find a matching Locale between the supported Locale and system Locales.
     *
     * @param supportedLocale one of your app supported locales
     * @param systemLocales   a list of the configured  locales in system preferences
     * @return a {@link MatchingLocales} containing the pair of matching locales. If no match is found null is returned
     */
    MatchingLocales findMatch(Locale supportedLocale, List<Locale> systemLocales);
}
