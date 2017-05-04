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

import com.franmontiel.localechanger.sample.pageobjects.SampleActivityScreen;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SampleActivityTest {

    private final Locale LOCALE_ES_ES = new Locale("es", "ES");
    private final String BUTTON_TEXT_ES = "Actualizar Locale";

    @Test
    public void shouldChangeLocale_WhenUserChangesItManually() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    @Test
    public void shouldMaintainLocale_WhenConfigurationChanged() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    @Test
    public void shouldUpdateButtonText_WhenLocaleChanged() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    @Test
    public void shouldMaintainButtonText_WhenConfigurationChanged() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    @Test
    public void shouldUpdateDate_WhenLocaleChanged() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    @Test
    public void shouldMaintainDate_WhenConfigurationChanged() {
        SampleActivityScreen sampleActivityScreen = new SampleActivityScreen();

        sampleActivityScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    // TODO Test Actionbar title

}