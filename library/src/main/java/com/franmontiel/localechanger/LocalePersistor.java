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

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * This class provides persistence
 */
class LocalePersistor {

    private static final String SP_LOCALE = "LocaleChanger.LocalePersistence";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_VARIANT = "variant";

    private static SharedPreferences localeSharedPrefs;

    LocalePersistor(Context context) {
        localeSharedPrefs = context.getSharedPreferences(SP_LOCALE, Context.MODE_PRIVATE);
    }

    @Nullable
    Locale load() {
        Locale locale = null;
        String language = localeSharedPrefs.getString(KEY_LANGUAGE, "");
        if (!language.isEmpty()) {
            locale = new Locale(
                    language,
                    localeSharedPrefs.getString(KEY_COUNTRY, ""),
                    localeSharedPrefs.getString(KEY_VARIANT, "")
            );
        }
        return locale;
    }

    void save(@NonNull Locale locale) {
        SharedPreferences.Editor editor = localeSharedPrefs.edit();
        editor.putString(KEY_LANGUAGE, locale.getLanguage());
        editor.putString(KEY_COUNTRY, locale.getCountry());
        editor.putString(KEY_VARIANT, locale.getVariant());
        editor.apply();
    }

    void clear() {
        SharedPreferences.Editor editor = localeSharedPrefs.edit();
        editor.clear();
        editor.apply();
    }
}
