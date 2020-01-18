package com.gojek.application.di.component;


import com.gojek.application.base.ViewModelFactory;
import com.gojek.application.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class})
public interface ApplicationComponent {

    ViewModelFactory provideViewModelFactory();
}
