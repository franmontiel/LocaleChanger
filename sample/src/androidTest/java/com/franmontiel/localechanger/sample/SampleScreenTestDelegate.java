/*
 * Copyright (c)  2018  Francisco José Montiel Navarro.
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

import com.franmontiel.localechanger.sample.pageobjects.SampleScreen;

import java.util.Locale;

/**
 * Advice: It is recommended to run the tests with a system locale configured not listed in the supported ones (to avoid false positives).
 */
final public class SampleScreenTestDelegate {

    private final Locale LOCALE_EN_EN = new Locale("en", "US");
    private final Locale LOCALE_ES_ES = new Locale("es", "ES");
    private static final Locale LOCALE_AR_JO = new Locale("ar", "JO");

    private final String BUTTON_TEXT_ES = "Actualizar Locale";
    private static final String SETTINGS_ITEM_TITLE_ES = "Preferencias";

    private SampleScreen sampleScreen;

    public void setUp(SampleScreen sampleScreen) {
        this.sampleScreen = sampleScreen;
    }

    void shouldChangeLocale_WhenUserChangesItManually() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    void shouldMaintainLocale_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyLocaleChanged(LOCALE_ES_ES)
                .changeOrientationToPortrait()
                .verifyLocaleChanged(LOCALE_ES_ES)
                .changeOrientationToLandscape()

                .verifyLocaleChanged(LOCALE_ES_ES)
                .changeOrientationToPortrait()
                .verifyLocaleChanged(LOCALE_ES_ES);
    }

    void shouldUpdateButtonText_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    void shouldMaintainButtonText_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyUpdateButtonText(BUTTON_TEXT_ES)
                .changeOrientationToPortrait()
                .verifyUpdateButtonText(BUTTON_TEXT_ES)

                .changeOrientationToLandscape()
                .verifyUpdateButtonText(BUTTON_TEXT_ES)
                .changeOrientationToPortrait()
                .verifyUpdateButtonText(BUTTON_TEXT_ES);
    }

    void shouldUpdateDate_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    void shouldMaintainDate_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES))
                .changeOrientationToPortrait()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES))

                .changeOrientationToLandscape()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES))
                .changeOrientationToPortrait()
                .verifyDate(DateProvider.provideLocaleFormattedDate(LOCALE_ES_ES));
    }

    void shouldUpdateItemTitle_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .changeLocale(LOCALE_ES_ES)
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES);
    }

    void shouldMaintainItemTitle_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .changeOrientationToLandscape()
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES)
                .pressBack()
                .changeOrientationToPortrait()
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES)
                .pressBack()

                .changeOrientationToLandscape()
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES)
                .pressBack()
                .changeOrientationToPortrait()
                .verifyOverflowSettingsItemTitle(SETTINGS_ITEM_TITLE_ES);
    }

    // TODO Check why this test only works on Nougat.
    // For some reason calling Locale.setDefault causes a NoActivityResumedException(Pressed back and killed the app) when invoking pressBack()
    void shouldUpdateLocale_WhenResumed_IfLocaleHasBeenChanged() throws Exception {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_ES_ES)
                .openNewScreen()
                .changeLocale(LOCALE_EN_EN)
                .pressBack()
                .verifyLocaleChanged(LOCALE_EN_EN);
    }

    void shouldChangeLayoutDirection_WhenLocaleChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .verifyLayoutDirectionLTR()
                .changeLocale(LOCALE_AR_JO)
                .verifyLayoutDirectionRTL();
    }

    void shouldMaintainLayoutDirection_WhenConfigurationChanged() {
        sampleScreen
                .launch()
                .changeLocale(LOCALE_EN_EN)
                .verifyLayoutDirectionLTR()
                .changeLocale(LOCALE_AR_JO)
                .verifyLayoutDirectionRTL()
                .changeOrientationToLandscape()
                .verifyLayoutDirectionRTL()
                .changeOrientationToPortrait()
                .verifyLayoutDirectionRTL()

                .changeOrientationToLandscape()
                .verifyLayoutDirectionRTL()
                .changeOrientationToPortrait()
                .verifyLayoutDirectionRTL();
    }

}