package com.narmware.doormojo.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.narmware.doormojo.MyApplication;
import com.narmware.doormojo.R;
import com.narmware.doormojo.helper.Constants;


public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{

    String mUrl;
    String book_id;
    WebView mWebView;
    TextView mTxtTitle;
    ImageButton mImgBtnBack;
    protected Dialog mNoConnectionDialog;
    protected Dialog mBlankDialog;
    protected ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        book_id=intent.getStringExtra(Constants.BOOKING_ID);
        init();
    }

    private void init() {

   mTxtTitle= (TextView) findViewById(R.id.web_title);

        mWebView= (WebView) findViewById(R.id.webview);
        setWebView();

            mUrl = MyApplication.URL_PAYMENT+Constants.BOOKING_ID+"="+book_id;

        Log.e("Payment url",mUrl);
        mWebView.loadUrl(mUrl);
        mImgBtnBack= (ImageButton) findViewById(R.id.btn_back);
        mImgBtnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        finish();

    }

    public void setWebView() {

        mProgress = new ProgressDialog(WebViewActivity.this);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setIndeterminate(true);
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //   webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
    }


    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            try {
                if (mProgress.isShowing() == false) {
                    mProgress.show();
                }
                else {
                    Log.d("Page Error : ","");
                    mProgress.dismiss();
                }
            }
            catch (Exception e) {
                Log.d("Page Exception : ","");
                mProgress.dismiss();
            }

           /* if(MyApplication.LOGOUT_URL.equals(url)){
                mWebView.loadUrl(mUrl);
                mProgress.dismiss();
            }*/
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            if(mProgress.isShowing()) {
                mProgress.dismiss();
            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            // TODO Auto-generated method stub

            view.loadUrl(url);
            mProgress.dismiss();
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("Page loaded : ", url);
            mProgress.dismiss();

            if( mBlankDialog != null) {
                if (mBlankDialog.isShowing()) {
                    mBlankDialog.dismiss();
                }

            }

           /* if(MyApplication.LOGOUT_URL.equals(url)){
                //    Toast.makeText(NavigationMenuActivity.this,"onPagefinished",Toast.LENGTH_SHORT).show();
                SharedPreferncesHelper.setLoginSharedPref(0,NavigationMenuActivity.this);
                Intent intent=new Intent(NavigationMenuActivity.this,LoginScreenActivity.class);
                startActivity(intent);
                finish();

            }*/

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                 Log.e("errorCode" ,description);
           // showNoConnectionDialog();
        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            mProgress.dismiss();
            return super.onRenderProcessGone(view, detail);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            //mHorizontalProgress.setProgress(progress);
        }


    }

}
