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


import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by fj on 31/03/17.
 */

public class LocaleChangerDelegateTest {

    private final static Locale ANY_LOCALE = new Locale("anyLanguage");

    private LocalePersistor localePersistor;
    private LocaleResolver localeResolver;
    private DefaultResolvedLocalePair defaultLocalePair;

    private AppLocaleChanger appLocaleChanger;
    private LocaleChangerDelegate sut;

    @Before
    public void setUp() {
        localePersistor = mock(LocalePersistor.class);
        localeResolver = mock(LocaleResolver.class);
        defaultLocalePair = mock(DefaultResolvedLocalePair.class);
        doReturn(defaultLocalePair).when(localeResolver).resolveDefault();
        appLocaleChanger = mock(AppLocaleChanger.class);
        sut = new LocaleChangerDelegate(localePersistor, localeResolver, appLocaleChanger);
    }

    @Test
    public void ShouldLoadPersistedLocale_WhenInitialized() {
        // Given

        // When
        sut.initialize();

        // Then
        verify(localePersistor).load();
    }

    @Test
    public void ShouldGetDefaultMatch_WhenInitialized_GivenNotPersisted() {
        // Given
        doReturn(null).when(localePersistor).load();

        // When
        sut.initialize();

        // Then
        verify(localeResolver).resolveDefault();
    }

    @Test
    public void ShouldPersistSupportedLocale_WhenInitialized_GivenNotPersisted() {
        // Given
        doReturn(null).when(localePersistor).load();
        doReturn(defaultLocalePair).when(localeResolver).resolveDefault();
        doReturn(ANY_LOCALE).when(defaultLocalePair).getSupportedLocale();

        // When
        sut.initialize();

        // Then
        verify(localePersistor).save(ANY_LOCALE);
    }

    @Test
    public void ShouldGetMatch_WhenInitialized_GivenPersisted() throws Exception {
        // Given
        doReturn(ANY_LOCALE).when(localePersistor).load();

        // When
        sut.initialize();

        // Then
        verify(localeResolver).resolve(ANY_LOCALE);
    }

    @Test
    public void ShouldChangeAppLocale_WhenInitialized_GivenNotPersisted() {
        // Given
        doReturn(null).when(localePersistor).load();

        // When
        sut.initialize();

        // Then
        verify(appLocaleChanger).change(defaultLocalePair.getResolvedLocale());
    }


    @Test
    public void ShouldChangeAppLocale_WhenInitialized_GivenPersisted() throws UnsupportedLocaleException {
        // Given
        doReturn(ANY_LOCALE).when(localePersistor).load();
        doReturn(ANY_LOCALE).when(localeResolver).resolve(ANY_LOCALE);

        // When
        sut.initialize();

        // Then
        verify(appLocaleChanger).change(ANY_LOCALE);
    }

    @Test
    public void ShouldPersistLocaleWithSupportedLocale_WhenChanged_GivenSupportedLocale() {
        // Given

        // When
        sut.setLocale(ANY_LOCALE);

        // Then
        verify(localePersistor).save(ANY_LOCALE);
    }

    @Test
    public void ShouldChangeAppLocaleWithAMatchingLocale_WhenChanged_GivenSupportedLocale() throws UnsupportedLocaleException {
        // Given
        doReturn(ANY_LOCALE).when(localeResolver).resolve(ANY_LOCALE);

        // When
        sut.setLocale(ANY_LOCALE);

        // Then
        verify(appLocaleChanger).change(ANY_LOCALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowIllegalArgumentException_WhenChanged_GivenNotSupportedLocale() throws UnsupportedLocaleException {
        // Given
        doThrow(IllegalArgumentException.class).when(localeResolver).resolve(ANY_LOCALE);

        // When
        sut.setLocale(ANY_LOCALE);

        // Then -> expected exception
    }

    @Test
    public void ShouldLoadPersistedLocale_WhenGetCurrentSupportedLocale() {
        // Given
        doReturn(ANY_LOCALE).when(localePersistor).load();

        // When
        Locale currentSupportedLocale = sut.getLocale();

        // Then
        assertEquals(ANY_LOCALE, currentSupportedLocale);
    }
}
