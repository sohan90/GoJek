package com.gojek.application.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.gojek.application.model.Repository;
import com.gojek.application.model.TrendingResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;

@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {

    HomeViewModel homeViewModel;

    @Mock
    Repository repository;

    @Mock
    MutableLiveData<TrendingResponse> trendingResponseMutableLiveData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homeViewModel = new HomeViewModel(repository);
    }

    @Test
    public void callTrendingApiService_returnWithSuccessResponse(){
        TrendingResponse trendingResponse = new TrendingResponse();
        Mockito.doReturn(Single.just(trendingResponse)).when(repository).getTrendingGitHub();
        homeViewModel.getTrendingGitHub();
        Mockito.verify(trendingResponseMutableLiveData).setValue(Mockito.any());
    }
}