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
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Locale;

class AppLocaleChanger {

    private Context context;

    AppLocaleChanger(Context context) {
        this.context = context;
    }

    void change(Locale newLocale) {
        Locale.setDefault(newLocale);

        if (SupportedSDK.hasNougat())
            updateConfiguration(newLocale);
        else
            updateConfigurationLegacy(newLocale);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void updateConfiguration(Locale newLocale) {
        context.getResources().getConfiguration().setLocale(newLocale);
    }

    @SuppressWarnings("deprecation")
    private void updateConfigurationLegacy(Locale newLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = newLocale;
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }

    Context configureBaseContext(Context context, Locale locale) {
        if (SupportedSDK.hasJellyBeanMr1()) {
            return getLocaleConfiguredContext(context, locale);
        } else {
            return context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Context getLocaleConfiguredContext(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        return context.createConfigurationContext(conf);
    }

    private static class SupportedSDK {

        static boolean hasNougat() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
        }

        static boolean hasJellyBeanMr1(){
          return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;

        }
    }
}
