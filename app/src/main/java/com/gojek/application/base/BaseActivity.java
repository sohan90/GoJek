package com.gojek.application.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gojek.application.di.component.ActivityComponent;
import com.gojek.application.di.component.DaggerActivityComponent;
import com.gojek.application.di.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(GoJekApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();

    }
}
