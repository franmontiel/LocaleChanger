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

import com.franmontiel.localechanger.matcher.LanguageMatchingAlgorithm;
import com.franmontiel.localechanger.matcher.MatchingAlgorithm;
import com.franmontiel.localechanger.matcher.MatchingLocales;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageMatchingAlgorithmTest {

    private static final Locale LOCALE_ES_ES = new Locale("es", "ES");
    private static final Locale LOCALE_IT_IT = new Locale("it", "IT");
    private static final Locale LOCALE_FR_CA = new Locale("fr", "CA");
    private static final Locale LOCALE_EN_GB = new Locale("en", "GB");

    private static final Locale LOCALE_EN_US = new Locale("en", "US");
    private static final Locale LOCALE_FR_FR = new Locale("fr", "FR");

    private static final List<Locale> SYSTEM_LOCALES = Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR);

    private MatchingAlgorithm sut;

    @Before
    public void setUp() throws Exception {
        sut = new LanguageMatchingAlgorithm();
    }

    @Test
    public void ShouldNotFindMatchingLocales_WhenFindDefaultMatch_GivenNonMatchingLocales() {
        // Given
        List<Locale> supportedLocalesNotMatching = Arrays.asList(LOCALE_ES_ES, LOCALE_IT_IT);

        // When
        MatchingLocales matchingLocales = sut.findDefaultMatch(supportedLocalesNotMatching, SYSTEM_LOCALES);

        // Then
        Assert.assertEquals(null, matchingLocales);
    }

    @Test
    public void ShouldFindFirstSystemLanguageMatchingLocale_WhenFindDefaultMatch_GivenMatchingLocales() {
        // Given
        List<Locale> supportedLocalesWithMultipleMatches = Arrays.asList(LOCALE_ES_ES, LOCALE_FR_CA, LOCALE_EN_GB);

        // When
        MatchingLocales matchingLocales = sut.findDefaultMatch(supportedLocalesWithMultipleMatches, SYSTEM_LOCALES);

        // Then
        Assert.assertEquals(new MatchingLocales(LOCALE_EN_GB, LOCALE_EN_US), matchingLocales);
    }

    @Test
    public void ShouldNotFindMatchingLocales_WhenFindMatch_GivenNonMatchingLocale() {
        // Given
        Locale nonMatchingLocale = LOCALE_ES_ES;

        // When
        MatchingLocales matchingLocales = sut.findMatch(nonMatchingLocale, SYSTEM_LOCALES);

        // Then
        Assert.assertEquals(null, matchingLocales);
    }

    @Test
    public void ShouldFindFirstSystemLanguageMatchingLocale_WhenFindMatch_GivenMatchingLocale() {
        // Given
        Locale matchingLocale = LOCALE_FR_CA;

        // When
        MatchingLocales matchingLocales = sut.findMatch(matchingLocale, SYSTEM_LOCALES);

        // Then
        Assert.assertEquals(new MatchingLocales(LOCALE_FR_CA, LOCALE_FR_FR), matchingLocales);
    }
}
