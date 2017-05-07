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

package com.franmontiel.localechanger.sample;

import android.support.test.runner.AndroidJUnit4;

import com.franmontiel.localechanger.sample.pageobjects.SampleScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * Advice: It is recommended to run the tests with a system locale configured not listed in the supported ones (to avoid false positives).
 */
@RunWith(AndroidJUnit4.class)
public class SampleFragmentTest {

    private final Locale LOCALE_EN_EN = new Locale("en", "US");
    private final Locale LOCALE_ES_ES = new Locale("es", "ES");
    private final String BUTTON_TEXT_ES = "Actualizar Locale";
    private static final String SETTINGS_ITEM_TITLE_ES = "Preferencias";

    private SampleScreen sampleScreen;

    @Before
    public void setUp() {
        sampleScreen = new SampleScreen(SampleFragmentContainerActivity.class);
    }

    @Test
    public void shouldChangeLocale_WhenUserChangesItManually() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    @Test
    public void shouldMaintainLocale_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .changeOrientationToPortrait()
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    @Test
    public void shouldUpdateButtonText_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    @Test
    public void shouldMaintainButtonText_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .changeOrientationToPortrait()
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    @Test
    public void shouldUpdateDate_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    @Test
    public void shouldMaintainDate_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .changeOrientationToPortrait()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    @Test
    public void shouldUpdateItemTitle_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES);
    }

    @Test
    public void shouldMaintainItemTitle_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .changeOrientationToPortrait()
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES);
    }

    // TODO Test Actionbar title

}