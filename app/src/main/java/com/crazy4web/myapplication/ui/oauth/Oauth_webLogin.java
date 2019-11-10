package com.crazy4web.myapplication.ui.oauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crazy4web.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Oauth_webLogin extends AppCompatActivity {

    WebView webView;
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 5.1.0; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_web_login);
//        webView = findViewById(R.id.webview_oauth);
//
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        webView.getSettings().setUserAgentString(USER_AGENT);
//
//        webView.setWebViewClient(new WebViewClient());
//
//        webView.loadUrl("https://girik.operatoroverload.com/facebook/");


//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            webView.evaluateJavascript(
//                    "(function() { return ('<html>'+document.getElementsByTagName('h1').value+'</html>'); })();",
//                    new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String html) {
//                            Log.d("viewweb", "onReceiveValue: "+html);
//                            // code here
//                        }
//                    });
//        }
//    }



    }
}
