package com.gojek.application.di.module;

import com.gojek.application.di.scope.ActivityScope;
import com.gojek.application.model.TrendingResponse;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module(includes = ActivityModule.class)
public class AdapterModule {

    @ActivityScope
    @Provides
    public List<TrendingResponse> provideList() {
        return new ArrayList<>();
    }


}
