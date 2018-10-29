package me.mikasa.retrofitdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.mikasa.retrofitdemo.R;
import me.mikasa.retrofitdemo.base.BaseToolbarActivity;
import me.mikasa.retrofitdemo.fragment.MovieFragment;

public class DoubanActivity extends BaseToolbarActivity {
    @Override
    protected int setLayoutResId() {
        return R.layout.activity_douban;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void initData() {
        mTitle.setText("豆瓣电影");
    }

    @Override
    protected void initView() {
        MovieFragment movieFragment=MovieFragment.getInstance();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.container_layout,movieFragment);
        transaction.commit();

    }

    @Override
    protected void initListener() {

    }
}
