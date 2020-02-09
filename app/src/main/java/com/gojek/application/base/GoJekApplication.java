package com.gojek.application.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.gojek.application.di.component.ApplicationComponent;
import com.gojek.application.di.component.DaggerApplicationComponent;
import com.gojek.application.di.module.AppModule;
import com.gojek.application.di.module.HttpModule;

public class GoJekApplication extends Application {

    private static ApplicationComponent sComponent;
    private static GoJekApplication sInstance ;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
    }

    public static GoJekApplication getInstance() {
        return sInstance;
    }

    public static ApplicationComponent getAppComponent() {
        if (sComponent == null) {
            return DaggerApplicationComponent.builder()
                    .appModule(new AppModule(sInstance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return sComponent;
    }
}
