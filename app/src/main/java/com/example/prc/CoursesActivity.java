package com.example.prc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class CoursesActivity extends AppCompatActivity {

    private WebView mywebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        mywebView = (WebView) findViewById(R.id.webview);
        Toast.makeText(getApplicationContext(), "Please wait for few seconds until website is load", Toast.LENGTH_LONG).show();
        WebSettings websettings=mywebView.getSettings();
        websettings.setJavaScriptEnabled(true);
        mywebView.loadUrl("https://palmistryresearchcenter.com/courses.html");
        mywebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(mywebView.canGoBack()){
            mywebView.goBack();
        }
        else
            super.onBackPressed();

    }
}