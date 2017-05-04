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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateProvider {

    private static final long A_DATE = 1493769600000L;

    public static String provideSystemLocaleFormattedDate() {
        return SimpleDateFormat.getDateInstance(DateFormat.LONG).format(new Date(A_DATE));
    }

    public static String provideLocaleFormattedDate(Locale locale) {
        return SimpleDateFormat.getDateInstance(DateFormat.LONG, locale).format(new Date(A_DATE));

    }
}
