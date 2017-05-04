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

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * An Espresso ViewAction that changes the orientation of the screen
 */
public class OrientationChangeAction implements ViewAction {
    private final int orientation;

    private OrientationChangeAction(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "change orientation to " + orientation;
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadUntilIdle();
        final Activity activity = (Activity) view.getContext();
        activity.setRequestedOrientation(orientation);

//        Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
//        if (resumedActivities.isEmpty()) {
//            throw new RuntimeException("Could not change orientation");
//        }
    }

    public static ViewAction orientationLandscape() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static ViewAction orientationPortrait() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}