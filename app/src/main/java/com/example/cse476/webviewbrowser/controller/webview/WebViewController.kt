package com.example.cse476.webviewbrowser.controller.webview

import android.graphics.drawable.Drawable
import android.webkit.WebView

class WebViewController(webView: WebView, index: Int) {
    private val _webView = webView
    var webSiteName: String = "Tab " + (index + 1)
    var icon: Drawable? = null

    fun goToWebSite(url: String) {
        this._webView.loadUrl(url)
    }

    fun pauseWebView() {
        this._webView.onPause()
    }

    fun continueWebView() {
        this._webView.onResume()
    }

    fun getUrl(): String {
        return this._webView.url ?: ""
    }

    fun stopWebView() {
        this._webView.stopLoading()
        this._webView.removeAllViews()
        this._webView.destroy()
    }
}