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

package com.franmontiel.localechanger.sample.pageobjects;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.franmontiel.localechanger.sample.R;
import com.franmontiel.localechanger.sample.SampleActivity;

import org.junit.Rule;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.franmontiel.localechanger.sample.OrientationChangeAction.orientationLandscape;
import static com.franmontiel.localechanger.sample.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class SampleActivityScreen {

    private final static int LOCALE_SPINNER_ID = R.id.localeSpinner;
    private final static int UPDATE_BUTTON_ID = R.id.localeUpdate;

    @Rule
    private ActivityTestRule<SampleActivity> activityRule = new ActivityTestRule<>(SampleActivity.class);

    public SampleActivityScreen launch() {
        activityRule.launchActivity(null);
        return this;
    }

    public SampleActivityScreen changeLocale(Locale locale) {
        onView(withId(LOCALE_SPINNER_ID)).perform(click());
        onData(allOf(is(instanceOf(Locale.class)), is(locale))).perform(click());
        onView(withId(LOCALE_SPINNER_ID)).check(matches(withSpinnerText(locale.toString())));

        onView(withId(UPDATE_BUTTON_ID)).perform(click());
        return this;
    }

    public SampleActivityScreen verifyLocaleChanged(Locale expectedLocale) {
        onView(withId(R.id.currentLocale)).check(matches(withText(expectedLocale.toString())));
        return this;
    }

    public SampleActivityScreen verifyDate(String expectedDateFormat) {
        onView(withId(R.id.date)).check(matches(withText(expectedDateFormat)));
        return this;
    }

    public SampleActivityScreen verifyUpdateButtonText(String expectedText) {
        onView(withId(R.id.localeUpdate)).check(matches(withText(expectedText)));
        return this;
    }

    public SampleActivityScreen changeOrientationToLandscape() {
        onView(isRoot()).perform(orientationLandscape());
        SystemClock.sleep(100);
        return this;
    }

    public SampleActivityScreen changeOrientationToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
        SystemClock.sleep(100);
        return this;
    }
}
