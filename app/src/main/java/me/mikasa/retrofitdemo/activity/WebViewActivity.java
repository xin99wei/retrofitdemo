package me.mikasa.retrofitdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import me.mikasa.retrofitdemo.R;
import me.mikasa.retrofitdemo.base.BaseToolbarActivity;

public class WebViewActivity extends BaseToolbarActivity {
    private WebView webView;
    private String url;
    private ProgressBar loadingProgress;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        url=intent.getStringExtra("url");
        mTitle.setText(title);
    }

    @Override
    protected void initView() {
        loadingProgress=findViewById(R.id.loadingProgress);
        webView=findViewById(R.id.webview);
        //webSettings=webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(url);
    }
    WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);//??
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    WebChromeClient chromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            loadingProgress.setProgress(newProgress);
            if (newProgress==100){
                loadingProgress.setVisibility(View.GONE);
            }
        }
    };
    @Override
    protected void initListener() {
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}
