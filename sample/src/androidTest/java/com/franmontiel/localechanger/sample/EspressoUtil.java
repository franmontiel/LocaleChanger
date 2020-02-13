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
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import java.util.Collection;

import static androidx.test.runner.lifecycle.Stage.RESUMED;

public class EspressoUtil {
    public static Activity getCurrentActivity() {
        final Activity[] currentActivity = {null};

        Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
        if (resumedActivities.iterator().hasNext()) {
            currentActivity[0] = (Activity) resumedActivities.iterator().next();
        }


        return currentActivity[0];
    }
}
