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

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class LocaleResolverTest {

    private static final Locale LOCALE_ES_ES = new Locale("es", "ES");
    private static final Locale LOCALE_IT_IT = new Locale("it", "IT");

    private static final Locale LOCALE_EN_US = new Locale("en", "US");
    private static final Locale LOCALE_FR_FR = new Locale("fr", "FR");

    private static final LocalePreference ANY_PREFERENCE = LocalePreference.PreferSystemLocale;

    @Test
    public void ShouldResolveFirstSupportedLocaleAsDefault_WhenResolveDefaultLocale_GivenNonMatchingLocales() {
        // Given
        LocaleResolver sut = new LocaleResolver(
                Arrays.asList(LOCALE_ES_ES, LOCALE_IT_IT),
                Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR),
                new LanguageMatchingAlgorithm(),
                ANY_PREFERENCE
        );

        // When
        DefaultResolvedLocalePair defaultResolvedLocalePair = sut.resolveDefault();

        // Then
        Assert.assertEquals(LOCALE_ES_ES, defaultResolvedLocalePair.getResolvedLocale());
    }

    @Test
    public void ShouldResolveGivenSupportedLocaleAsDefault_WhenResolveLocale_GivenNonMatchingLocales() throws UnsupportedLocaleException {
        // Given
        LocaleResolver sut = new LocaleResolver(
                Arrays.asList(LOCALE_ES_ES, LOCALE_IT_IT),
                Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR),
                new LanguageMatchingAlgorithm(),
                ANY_PREFERENCE
        );

        // When
        Locale resolvedLocale = sut.resolve(LOCALE_IT_IT);

        // Then
        Assert.assertEquals(LOCALE_IT_IT, resolvedLocale);
    }

    @Test
    public void ShouldResolveGivenSupportedLocaleDirectly_WhenResolveLocale_PreferringSupportedLocale() throws UnsupportedLocaleException {
        // Given
        MatchingAlgorithm matchingAlgorithm = mock(MatchingAlgorithm.class);

        LocaleResolver sut = new LocaleResolver(
                Arrays.asList(LOCALE_ES_ES, LOCALE_IT_IT),
                Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR),
                matchingAlgorithm,
                LocalePreference.PreferSupportedLocale
        );

        // When
        Locale resolvedLocale = sut.resolve(LOCALE_IT_IT);

        // Then
        verify(matchingAlgorithm, never()).findMatch(LOCALE_IT_IT, Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR));
        Assert.assertEquals(LOCALE_IT_IT, resolvedLocale);
    }

    @Test(expected = UnsupportedLocaleException.class)
    public void ShouldThrowException_WhenResolveLocale_GivenNotSupportedLocale() throws UnsupportedLocaleException {
        // Given
        LocaleResolver sut = new LocaleResolver(
                Arrays.asList(LOCALE_ES_ES, LOCALE_IT_IT),
                Arrays.asList(LOCALE_EN_US, LOCALE_FR_FR),
                new LanguageMatchingAlgorithm(),
                ANY_PREFERENCE
        );
        Locale unsupportedLocale = new Locale("nl", "BE");

        // When
        sut.resolve(unsupportedLocale);

        // Then -> throws expected exception
    }
}
