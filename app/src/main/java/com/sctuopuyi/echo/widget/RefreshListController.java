package com.sctuopuyi.echo.widget;

import android.view.View;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sctuopuyi.echo.databinding.LayoutCommonRecyclerViewBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import io.ditclear.bindingadapter.BindingViewAdapter;

import java.util.List;

public abstract class RefreshListController<T> {

    //region declare variable

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View llEmptyView;
    private BindingViewAdapter adapter;
    private ObservableArrayList<T> dataSource;
    private int currentPage;
    private OnRefreshListener refreshListener;

    //endregion

    //region method

    public RefreshListController(LayoutCommonRecyclerViewBinding mBinding, ObservableArrayList<T> dataSource) {
        this.dataSource = dataSource;
        this.refreshLayout = mBinding.srlRoot;
        this.recyclerView = mBinding.rvList;
        this.llEmptyView = mBinding.llEmpty;
        initController();
    }

    private void initController() {
        adapter = injectAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getList(currentPage + 1);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshListener != null) {
                    refreshListener.onRefresh(refreshLayout);
                }
                currentPage = 0;
                getList(1);
            }
        });
    }

    public void refresh() {
        currentPage = 0;
        dataSource.clear();
        getList(1);
    }

    public void onNewData(int total, List<T> dataList) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (currentPage == 0) dataSource.clear();
        if (!dataList.isEmpty()) currentPage++;

        dataSource.addAll(dataList);
        // 总数据量大于已经加载完的数据，才允许加载更多，等于或小于不允许加载更多
        refreshLayout.setEnableLoadMore(total > dataSource.size());
        //判断是否为空列表，显隐占位空图片
        if (dataSource.isEmpty()) {
            llEmptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            llEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public abstract void getList(int page);

    public abstract BindingViewAdapter injectAdapter();

    //endregion

    //region no used

    public RefreshListController<T> setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    public BindingViewAdapter getAdapter() {
        return adapter;
    }

    public List<T> getDataSource() {
        return dataSource;
    }

    //endregion

}
