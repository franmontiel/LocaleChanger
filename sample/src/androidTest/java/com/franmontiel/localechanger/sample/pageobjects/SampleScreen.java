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

import android.app.Activity;
import android.os.SystemClock;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.franmontiel.localechanger.sample.R;

import java.util.Locale;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.franmontiel.localechanger.sample.OrientationChangeAction.orientationLandscape;
import static com.franmontiel.localechanger.sample.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class SampleScreen {

    private final static int LOCALE_SPINNER_ID = R.id.localeSpinner;
    private final static int UPDATE_BUTTON_ID = R.id.localeUpdate;

    private ActivityTestRule<? extends Activity> activityRule;

    public SampleScreen(Class<? extends Activity> activityClass) {
        this.activityRule = new ActivityTestRule<>(activityClass, false, false);
    }

    public SampleScreen launch() {
        activityRule.launchActivity(null);
        return this;
    }

    public SampleScreen changeLocale(Locale locale) {
        onView(withId(LOCALE_SPINNER_ID)).perform(click());
        onData(allOf(is(instanceOf(Locale.class)), is(locale))).perform(click());

        onView(withId(UPDATE_BUTTON_ID)).perform(click());
        SystemClock.sleep(500);

        return this;
    }

    public SampleScreen openNewScreen() {
        onView(allOf(withId(R.id.openNewScreen), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());
        return this;
    }

    public SampleScreen verifyLocaleChanged(Locale expectedLocale) {
        onView(withId(R.id.currentLocale)).check(matches(withText(expectedLocale.toString())));
        return this;
    }

    public SampleScreen verifyDate(String expectedDateFormat) {
        onView(withId(R.id.date)).check(matches(withText(expectedDateFormat)));
        return this;
    }

    public SampleScreen verifyUpdateButtonText(String expectedText) {
        onView(withId(R.id.localeUpdate)).check(matches(withText(expectedText)));
        return this;
    }

    public SampleScreen verifyOverflowSettingsItemTitle(String expectedTitle) {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // The MenuItem id and the View id is not the same. It is needed to use another matcher.
        onView(withText(expectedTitle)).check(matches(isDisplayed()));

        return this;
    }

    public SampleScreen verifyLayoutDirectionRTL() {
        onView(withId(R.id.currentLocale)).check(isCompletelyLeftOf(withId(R.id.currentLocaleTitle)));

        return this;
    }

    public SampleScreen verifyLayoutDirectionLTR() {
        onView(withId(R.id.currentLocale)).check(isCompletelyRightOf(withId(R.id.currentLocaleTitle)));

        return this;
    }

    public SampleScreen changeOrientationToLandscape() {
        onView(isRoot()).perform(orientationLandscape());
        SystemClock.sleep(500);
        return this;
    }

    public SampleScreen changeOrientationToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
        SystemClock.sleep(500);
        return this;
    }

    public SampleScreen pressBack() {
        Espresso.pressBack();
        return this;
    }
}
