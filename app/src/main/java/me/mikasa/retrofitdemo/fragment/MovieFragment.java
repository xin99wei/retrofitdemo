package me.mikasa.retrofitdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mikasa.retrofitdemo.R;
import me.mikasa.retrofitdemo.activity.WebViewActivity;
import me.mikasa.retrofitdemo.adapter.MovieAdapter;
import me.mikasa.retrofitdemo.base.BaseFragment;
import me.mikasa.retrofitdemo.base.BaseRvAdapter;
import me.mikasa.retrofitdemo.bean.Movie;
import me.mikasa.retrofitdemo.bean.ResponseEntity;
import me.mikasa.retrofitdemo.http.Constant;
import me.mikasa.retrofitdemo.http.DoubanApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragment extends BaseFragment
        implements BaseRvAdapter.OnRvItemClickListener {
    private XRecyclerView recyclerView;
    private MovieAdapter mAdapter;
    private static MovieFragment sInstance;
    private List<Movie>movieList=null;
    private List<Movie>appendData=null;
    private static int count=0;

    public static MovieFragment getInstance(){
        if (sInstance==null){
            sInstance=new MovieFragment();
        }
        return sInstance;
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_xrv;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAdapter=new MovieAdapter(mBaseActivity);
        movieList=new ArrayList<>();
        appendData=new ArrayList<>();
    }

    @Override
    protected void initView() {
        recyclerView=mRootView.findViewById(R.id.xrv);
        recyclerView.setLayoutManager(new GridLayoutManager(mBaseActivity,2));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLimitNumberToCallLoadMore(1);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRvItemClickListener(this);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (movieList!=null){
                    movieList.clear();
                }
                getMovie();
            }

            @Override
            public void onLoadMore() {
                ++count;
                if (count==10){
                    showToast("没有更多了");
                    recyclerView.loadMoreComplete();
                }else {
                    loadMore();
                }
            }
        });
        recyclerView.refresh();//请求数据
    }
    private void loadMore(){
        Collections.shuffle(appendData);
        movieList.addAll(appendData);
        mAdapter.appendData(appendData);
        recyclerView.loadMoreComplete();//loadMoreComplete()
    }
    private void getMovie(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DoubanApi doubanApi=retrofit.create(DoubanApi.class);
        Call<ResponseEntity>call=doubanApi.getMovie();
        call.enqueue(new Callback<ResponseEntity>() {//异步
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                //response.body()映射ResponseEntity
                List<ResponseEntity.SubjectsBean>beans=response.body().getSubjects();//电影jsonArray
                recyclerView.refreshComplete();
                List<Movie>movies=getMovieList(beans);
                mAdapter.appendData(movies);//加载数据，更新UI
                appendData.addAll(movies);
            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                showToast("请求网络数据失败");
            }
        });
    }
    private List<Movie>getMovieList(List<ResponseEntity.SubjectsBean>beanList){
        for (ResponseEntity.SubjectsBean bean:beanList){
            Movie movie=new Movie();
            movie.setPosterUrl(bean.getImages().getSmall());
            String pingfen="评分："+String.valueOf(bean.getRating().getAverage());
            movie.setRating(pingfen);
            StringBuilder category=new StringBuilder();
            category.append("类型：");
            for (String s:bean.getGenres()){
                String str=s+" ";
                category.append(str);
            }
            movie.setCategory(category.toString());//StringBuilder.toString()
            movie.setTitle(bean.getTitle());
            movie.setOriginal_title(bean.getOriginal_title());
            movie.setLinkUrl(bean.getAlt());
            movieList.add(movie);
        }
        return movieList;
    }
    @Override
    public void onItemClick(int pos) {
        Intent intent=new Intent(mBaseActivity,WebViewActivity.class);
        intent.putExtra("title",movieList.get(pos-1).getTitle());//xrv有一个header
        intent.putExtra("url",movieList.get(pos-1).getLinkUrl());
        startActivity(intent);
    }
}
