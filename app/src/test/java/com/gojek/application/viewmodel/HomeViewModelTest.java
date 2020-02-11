package com.gojek.application.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.gojek.application.model.Repository;
import com.gojek.application.model.TrendingResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {

    HomeViewModel homeViewModel;

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();


    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    Repository repository;

    @Mock
    Observer<List<TrendingResponse>> observer;

    @Mock
    Observer<String> errorObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homeViewModel = new HomeViewModel(repository);
        homeViewModel.getListMutableLiveData().observeForever(observer);
        homeViewModel.getErrorStateLiveData().observeForever(errorObserver);
    }

    @Test
    public void getTrendingGitHub_trendingApiResponse_returnSuccess() {

        List<TrendingResponse> trendingResponses = new ArrayList<>();
        trendingResponses.add(new TrendingResponse());

        Single<List<TrendingResponse>> single = Single.just(trendingResponses);
        Mockito.when(repository.getTrendingGitHub()).thenReturn(single);
        homeViewModel.getTrendingGitHub();

        Assert.assertEquals(trendingResponses, homeViewModel.getListMutableLiveData().getValue());
        verify(observer).onChanged(trendingResponses);
    }

    @Test
    public void getTrendingGitHub_trendingApiResponse_returnFailure() {

        Throwable exception = new Throwable();
        when(repository.getTrendingGitHub()).thenReturn(Single.error(exception));
        homeViewModel.getTrendingGitHub();

        verify(observer, times(0)).onChanged(any());
        verify(errorObserver).onChanged(exception.getMessage());
    }
}