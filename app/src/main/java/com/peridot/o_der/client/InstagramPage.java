package com.peridot.o_der.client;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

public class InstagramPage extends AppCompatActivity {
    WebView webView;
   // String url = "https://m.instagram.com/oder3679/";
    String url = "https://www.instagram.com/p/CJ7-6pCHsHi/?utm_source=ig_web_copy_link";
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagrampage);

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
    }
}
