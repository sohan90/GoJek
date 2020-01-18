package com.gojek.application.di.module;

import androidx.lifecycle.ViewModel;

import com.gojek.application.base.ViewModelFactory;
import com.gojek.application.model.Repository;
import com.gojek.application.retrofit.Api;
import com.gojek.application.viewmodel.HomeViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class AppModule {

    @Singleton
    @Provides
    public Repository provideRepository(Api api) {
        return new Repository(api);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> value){
          return  new ViewModelFactory(value);
    }

    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public ViewModel provideViewModel(Repository repository){
        return new HomeViewModel(repository);
    }

}
