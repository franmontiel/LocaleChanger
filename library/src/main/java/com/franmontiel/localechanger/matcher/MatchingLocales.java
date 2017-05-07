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

import com.franmontiel.localechanger.LocalePreference;

import java.util.Locale;

/**
 * This class represents a pair of matching locales between a supported and a system Locale.
 */
public final class MatchingLocales {
    private Locale supportedLocale;
    private Locale systemLocale;

    public MatchingLocales(Locale supportedLocale, Locale systemLocale) {
        this.supportedLocale = supportedLocale;
        this.systemLocale = systemLocale;
    }

    public Locale getSupportedLocale() {
        return supportedLocale;
    }

    public Locale getSystemLocale() {
        return systemLocale;
    }

    public Locale getPreferredLocale(LocalePreference preference) {
        return preference.equals(LocalePreference.PreferSupportedLocale) ?
                supportedLocale :
                systemLocale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchingLocales that = (MatchingLocales) o;

        return supportedLocale.equals(that.supportedLocale)
                && systemLocale.equals(that.systemLocale);
    }

    @Override
    public int hashCode() {
        int result = supportedLocale.hashCode();
        result = 31 * result + systemLocale.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return supportedLocale.toString() + ", " + systemLocale.toString();
    }
}
