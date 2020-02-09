package com.gojek.application.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gojek.application.R;
import com.gojek.application.base.BaseActivity;
import com.gojek.application.base.ViewModelFactory;
import com.gojek.application.databinding.ActivityMainBinding;
import com.gojek.application.ui.adapter.TrendingAdapter;
import com.gojek.application.viewmodel.HomeViewModel;

import javax.inject.Inject;

public final class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    TrendingAdapter adapter;

    private ActivityMainBinding binding;

    private HomeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        inject();
        initViewModel();
        bindViewModel();
        bindMutableObserver();
        initUi();
        initListener();
    }

    private void inject() {
        getActivityComponent().inject(this);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).
                get(HomeViewModel.class);
    }

    private void bindViewModel() {
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initUi() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void bindMutableObserver() {
        viewModel.getErrorStateLiveData().observe(this, errorMsg -> {

            binding.noNetworkLyt.setVisibility(View.VISIBLE);
            binding.shimmer.stopShimmer();
            binding.shimmer.setVisibility(View.GONE);

        });

        viewModel.getListMutableLiveData().observe(this, trendingResponses -> {

            binding.refresh.setRefreshing(false);
            binding.shimmer.setVisibility(View.GONE);
            binding.shimmer.stopShimmer();
            binding.noNetworkLyt.setVisibility(View.GONE);
            binding.refresh.setVisibility(View.VISIBLE);

            adapter.setData(trendingResponses);
        });
    }

    private void initListener() {
        binding.refresh.setOnRefreshListener(this);
        binding.retryBtn.setOnClickListener(v -> {
            binding.refresh.setRefreshing(true);
            onRefresh();
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        binding.shimmer.stopShimmer();
    }

    @Override
    public void onRefresh() {
        viewModel.getTrendingGitHub();
    }

}
