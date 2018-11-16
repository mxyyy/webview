package com.bwie.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/*整体思路：
* 1.清单文件里面添加权限
* 2.写布局，初始化控件
* 3.通过点击事件，加载网页
* 4.初始化webview的控件
* 5.让WebView支持JS*/

public class MainActivity extends AppCompatActivity {

    private WebView www_wb;
    private EditText path_et;
    private ProgressBar webView_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
    }

    private void initView() {
        path_et = findViewById(R.id.path_et);
        webView_pb = findViewById(R.id.webView_pb);
        www_wb = findViewById(R.id.www_wb);

        //使用webview对象，进行的一些初始化设置
        webViewInit();
        //使用webViewSettings对webview进行一系列初始化的设置
        webViewSettingsInit();
    }



    private void webViewInit() {
        // setWebViewClient 此方法的作用是,当在webView进点击时,不跳转到游览器的设置(也就是不打开新的Activity),而是在本app里进行操作
        www_wb.setWebViewClient(new WebViewClient());

        // requestFocus 触摸焦点起作用（如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件
        www_wb.requestFocus();

        // setWebChromeClient 该监听事件是指UI(界面)发送改变时进行各监听.  onProgressChanged
        www_wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                首先通过代码让pRogressBar显示出来
                webView_pb.setVisibility(View.VISIBLE);
//                其次对progressBar设置加载进度的参数
                webView_pb.setProgress(newProgress);
                if (newProgress == 100) {
//                  加载完毕让进度条消失
                    webView_pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    /*对网页进行一系列的初始化设置*/
    private void webViewSettingsInit() {
        //      得到一个webView的设置对象,WebSettings.
        WebSettings settings = www_wb.getSettings();
        // setJavaScriptEnabled  使webView可以支持Javascript:
        settings.setJavaScriptEnabled(true);
        // setSupportZoom 使webView允许网页缩放,记住用这方法前,要有让webView支持Javascript的设定.否则会不起作用
        settings.setSupportZoom(true);
        //  setBlockNetworkImage  是webView只加载文字,而不加载图片,为用户省流量.
//        settings.setBlockNetworkImage(true);
    }


    //  通过点击事件,对网址加载,
    public void load(View v) {
        //进行非空的判断.
        String trim = path_et.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this, "不能输入为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //加载用户输入的网址,loadUrl()
        www_wb.loadUrl("http://" + trim);
    }


    //  通过点击事件,对网页进行前进的操作
    public void advance(View v) {
        //进行一个状态的判断,看是否可以前进,canGoForward
        if (www_wb.canGoForward()) {
            //可以前进,就进行前进操作,.goForward();
            www_wb.goForward();
        } else {
            Toast.makeText(this, "用户您好,前方无路", Toast.LENGTH_SHORT).show();
        }
    }

    //  通过点击事件,对网页进行后退操作
    public void back(View v) {
        //进行一个状态的判断,看是否可以后退,canGoBack
        if (www_wb.canGoBack()) {
            //可以后退就进行后退操作,goBack();
            www_wb.goBack();
        } else {
            Toast.makeText(this, "用户您好，已退无可退了", Toast.LENGTH_SHORT).show();
        }

    }

    //  通过点击事件,对网页进行刷新操作
    public void refresh(View v) {
        //对当前网页刷新,reload();
        www_wb.reload();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //这里重写返回键
            //进行一个状态的判断,看是否可以后退,canGoBack
            if (www_wb.canGoBack()) {
                //可以后退就进行后退操作,goBack();
                www_wb.goBack();
            } else {
                Toast.makeText(this, "用户您好，已退无可退了", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}
