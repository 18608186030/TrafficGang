package com.stjy.baselib.base.mvvm;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.ToastUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * AgentWeb基类
 *
 * @author superman
 */
public abstract class BaseAgentWebActivity extends BaseActivity {
    private final int REQUEST_CONTACT = 2001;
    protected AgentWeb mAgentWeb;
    protected BridgeWebView bridgeWebView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        buildAgentWeb();
    }

    protected void buildAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebView(getWebView())
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(getWebChromeClient())
                .setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK)
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .createAgentWeb()
                .ready()
                .go(getUrl());
    }

    /**
     * 同步WebView的Cookie
     */
    protected void syncCookie() {
//        String token = LoginUser.getInstance().getToken();
//        if (token != null && !token.isEmpty()) {
//            AgentWebConfig.syncCookie(getUrl(), "cityId=" + AddressUtil.getInstance().getCityId());
//            AgentWebConfig.syncCookie(getUrl(), "token=" + token);
//            if (getUserCallBackFunction != null) {
//                getUserCallBackFunction.onCallBack("sync_cookie_succeed");
//            }
//        } else {
//            AgentWebConfig.removeAllCookies();
//        }
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object o) {
        if (o instanceof Object) {
            //用户退出了登录
            AgentWebConfig.removeAllCookies();
        } else {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (bridgeWebView != null) {
            bridgeWebView.destroy();
            bridgeWebView = null;
        }

        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
            mAgentWeb = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 法大大摄像认证
         */
        if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK) {
            if (data != null) {
                Uri contactUri = data.getData();
                String[] queryFields = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY
                };
                Cursor cursor = this.getContentResolver().query(contactUri, queryFields, null, null, null);
                try {
                    if (null != cursor && cursor.moveToFirst()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));
                        String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //得到纯数字电话号码
                        if (phoneNum.startsWith("+86")) {
                            phoneNum = phoneNum.replace("+86", "");
                        }
                        phoneNum = phoneNum.replace(" ", "");
                        phoneNum = phoneNum.replace("-", "");
                        //if (getPhoneNumCallBackFunction != null) {
                        //    getPhoneNumCallBackFunction.onCallBack(GsonUtils.toJson(new ContactsBean(name, phoneNum)));
                        //}
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        }
    }

    protected abstract String getUrl();

    protected abstract ViewGroup getAgentWebParent();

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(getWebView());

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return mBridgeWebViewClient.shouldOverrideUrlLoading(view, request);  //兼容高版本，必须设置
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return mBridgeWebViewClient.shouldOverrideUrlLoading(view, url);  //兼容低版本，必须设置
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mBridgeWebViewClient.onPageStarted(view, url, favicon);  //必须设置
            }

            @Override
            public void onPageFinished(WebView view, String url) {  //必须设置
                mBridgeWebViewClient.onPageFinished(view, url);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        };
    }


    protected WebChromeClient getWebChromeClient() {
        return null;
    }

    protected MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return new MiddlewareWebChromeBase() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
                setBarTitle(title);
            }


            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                super.openFileChooser(valueCallback);
            }

            /**
             * android 端接收 H5 端发来的请求
             * For Android >= 3.0
             */
            @Override
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                super.openFileChooser(valueCallback, acceptType);
                /**
                 * 法大大摄像认证
                 */
                if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(valueCallback,
                        acceptType, BaseAgentWebActivity.this)) {
                    return;
                }
            }

            /**
             * android 端接收 H5 端发来的请求 For Android >= 4.1
             */
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                super.openFileChooser(uploadFile, acceptType, capture);
                /**
                 * 法大大摄像认证
                 */
                if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadFile,
                        acceptType, BaseAgentWebActivity.this)) {
                    return;
                }
            }

            /**
             * android 端接收 H5 端发来的请求 For Lollipop 5.0+ Devices
             */
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                /**
                 * 法大大摄像认证
                 */
                if (WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView,
                        filePathCallback, BaseAgentWebActivity.this, fileChooserParams)) {
                    return true;
                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        };
    }

    protected MiddlewareWebClientBase getMiddleWareWebClient() {
        return new MiddlewareWebClientBase() {
        };
    }

    protected BridgeWebView getWebView() {
        if (bridgeWebView == null) {
            bridgeWebView = new BridgeWebView(this);
            WebSettings webSettings = bridgeWebView.getSettings();
            String userAgentString = webSettings.getUserAgentString();
            webSettings.setUserAgentString(userAgentString + " safety");
            /**
             * 法大大摄像认证
             * 对 WebSettings 进行设置:添加 ua 字段和适配 h5 页面布局等 * @param mWebView 第三方的 WebView 对象
             * @param context 第三方上下文
             */
            WBH5FaceVerifySDK.getInstance().setWebViewSettings(bridgeWebView, this);
            bridgeWebView.registerHandler("androidToJS", (data, function) -> {
                ToastUtils.showLong(data);
                function.onCallBack(data);
            });
            syncCookie();
        }
        return bridgeWebView;
    }

    protected AgentWeb getAgentWeb() {
        return this.mAgentWeb;
    }
}
