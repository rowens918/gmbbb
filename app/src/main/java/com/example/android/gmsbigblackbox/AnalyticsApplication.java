/*
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.gmsbigblackbox;
import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    public static GoogleAnalytics analytics() {
        return analytics;
    }

    public static Tracker tracker() {
        return tracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GoogleAnalytics sAnalytics = GoogleAnalytics.getInstance(this);
        tracker = sAnalytics.newTracker(R.xml.global_tracker);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
        tracker.send(new HitBuilders.ScreenViewBuilder().setCustomDimension(1, null).build());
    }

   /* public static void addToTracker(String name) {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = new AnalyticsApplication();
        Tracker mTracker = application.getDefaultTracker();

        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    } */
}
