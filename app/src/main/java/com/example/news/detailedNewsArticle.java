package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class detailedNewsArticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news_article);
        WebView webView=findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        Intent intent=getIntent();
        if(intent!=null){
            String url=intent.getStringExtra("url");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url); // load the url on the web view
        }
    }
    // custom web view client class who extends WebViewClient
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            return true;
        }
}
}