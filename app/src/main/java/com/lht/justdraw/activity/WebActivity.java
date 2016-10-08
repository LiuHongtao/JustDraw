package com.lht.justdraw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lht.justdraw.R;
import com.lht.justdraw.util.FileUtil;

public class WebActivity extends AppCompatActivity {

    long time = 0;
    String html;

    TextView mTvMsg;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_web);
        setContentView(R.layout.activity_web);

        mWebView = (WebView)findViewById(R.id.webview);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);

        WebSettings webSettings = mWebView.getSettings();
        // 开启Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        WebViewClient wc = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mTvMsg.setText(
                        String.format(
                                getResources().getString(R.string.format_time),
                                System.currentTimeMillis() - time + ""));
            }
        };
        mWebView.setWebViewClient(wc);

        int count = getIntent().getIntExtra("count", 1000);
        html = FileUtil.getFromAssets(this, "just/index.html");
        html = html.replace("$count$", count + "");
        loadHtml();
    }

    private void loadHtml() {
        time = System.currentTimeMillis();
        mWebView.loadDataWithBaseURL("file:///android_asset/just/", html, "text/html", "utf-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadHtml();
                break;
        }
        return true;
    }
}
