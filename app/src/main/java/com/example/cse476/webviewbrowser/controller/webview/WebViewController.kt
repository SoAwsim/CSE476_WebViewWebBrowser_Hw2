package com.example.cse476.webviewbrowser.controller.webview

import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.webkit.WebView
import com.example.cse476.webviewbrowser.MainActivity

class WebViewController(webView: WebView, index: Int) {
    val tabName : SpannableString
        get() {
            if (this._icon == null)
                return SpannableString(this._webSiteName)

            val span = SpannableString("  " + this._webSiteName)
            val aspectRatio =
                this._icon!!.intrinsicWidth.toFloat() / this._icon!!.intrinsicHeight.toFloat()
            this._icon!!.setBounds(
                0,
                0,
                (MainActivity.textSize * aspectRatio).toInt(),
                MainActivity.textSize.toInt()
            )
            span.setSpan(
                ImageSpan(this._icon!!, ImageSpan.ALIGN_BOTTOM),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return span
        }

    private val _webView = webView
    private var _webSiteName: String = "Tab " + (index + 1)
    private var _icon: Drawable? = null

    fun updateWebSiteName(name: String) {
        this._webSiteName = name
    }

    fun updateWebSiteIcon(icon: Drawable?) {
        this._icon = icon
    }

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