package com.bwie.webviewjs;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String HTML_URL = "http://172.17.8.100/images/small/default/test.html";
    private WebSettings webViewSettings;
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.show_wb);
        webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            /**
             * 给WebView加一个事件监听对象（WebViewClient)并重写shouldOverrideUrlLoading
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 下方2行代码是指在当前的webview中跳转到新的url
                view.loadUrl(url);
                return true;
            }
        });

        // Android端定义一个方法,给js调用,
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void showToast(String content) {
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
            }
        }, "Android");

    }

    //调用js暴露的方法.格式固定:webView对象.loadUrl("javascript:js方法名(参数)");
    public void call(View v) {
        webView.loadUrl("javascript:changeInputValue('你好吖')");
    }

    //加载本地的文件.
    public void load(View v) {
        //webView.loadUrl("http://www.qq.com");
        webView.loadUrl(HTML_URL);
    }
}
