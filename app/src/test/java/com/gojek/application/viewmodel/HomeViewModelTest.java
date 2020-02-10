package com.gojek.application.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.gojek.application.model.Repository;
import com.gojek.application.model.TrendingResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {


    HomeViewModel homeViewModel;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    Repository repository;

    @Mock
    Observer<List<TrendingResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homeViewModel = new HomeViewModel(repository);
        homeViewModel.getListMutableLiveData().observeForever(observer);
    }

    @Test
    public void callTrendingApiService_returnWithSuccessResponse(){

        Single<List<TrendingResponse>> single = Single.just(new ArrayList<>());
        Mockito.when(repository.getTrendingGitHub()).thenReturn(single);
        homeViewModel.getTrendingGitHub();

        //then
        List<TrendingResponse> trendingResponses = homeViewModel.getListMutableLiveData().getValue();

        verify(observer).onChanged(new ArrayList<TrendingResponse>());
    }
}