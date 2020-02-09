package com.gojek.application.di.component;

import com.gojek.application.di.module.ActivityModule;
import com.gojek.application.di.module.AdapterModule;
import com.gojek.application.di.scope.ActivityScope;
import com.gojek.application.ui.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, AdapterModule.class})

public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

}
