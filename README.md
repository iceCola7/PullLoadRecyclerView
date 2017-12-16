# PullLoadRecyclerView
实现RecyclerView下拉刷新和加载更多
## 效果预览
![](https://github.com/bjchenxz/PullLoadRecyclerView/raw/master/gif/app.gif)
## 使用方式
### build.gradle文件
```
compile 'com.cxz.recyclerview:pullloadrecyclerview:1.0.0'
```

### 布局文件
```
<com.cxz.recyclerview.PullLoadMoreRecyclerView
    android:id="@+id/pullLoadMoreRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### java 代码
```
mPullLoadMoreRecyclerView.setRefreshing(true);// 设置是否可以刷新
mPullLoadMoreRecyclerView.setLinearLayout();// 设置 LayoutManager
mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener());// 设置回调监听
```