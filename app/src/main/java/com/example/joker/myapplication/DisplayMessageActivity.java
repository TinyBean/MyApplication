package com.example.joker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        /*TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);*/
        WebView webview = (WebView) findViewById(R.id.webView);
        webview.loadUrl(message);
        webview.setWebViewClient(new WebViewClient());
        webview.requestFocus();
    }
}
