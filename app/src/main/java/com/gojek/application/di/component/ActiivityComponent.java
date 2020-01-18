package com.gojek.application.di.component;

import com.gojek.application.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class})
public interface ActiivityComponent {

}
