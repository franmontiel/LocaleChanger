package com.franmontiel.localechanger.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.franmontiel.localechanger.LocaleChanger;

import java.util.List;

/**
 * Base {@link Application} class to inherit from with all needed configuration.
 */
public abstract class LocaleChangerBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeLocaleChanger();
    }

    /**
     * Call {@link LocaleChanger#initialize(Context, List)} in here
     */
    public abstract void initializeLocaleChanger();


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }
}
