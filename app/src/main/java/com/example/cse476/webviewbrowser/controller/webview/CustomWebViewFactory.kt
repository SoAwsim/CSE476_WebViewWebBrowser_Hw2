package com.example.cse476.webviewbrowser.controller.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cse476.webviewbrowser.tabpager.ITabPagerAdapter

class CustomWebViewFactory {
    companion object{
        private lateinit var _tabPagerAdapter: ITabPagerAdapter

        @JvmStatic
        fun setTabPager(pager: ITabPagerAdapter) {
            _tabPagerAdapter = pager
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun newCustomWebView(
        controller: WebViewController,
        context: Context,
        webSite: String? = null
    ): WebView {
        val webView = WebView(context)
        controller.attachResources(context.resources)
        controller.attachWebView(webView)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                controller.updateWebSiteName(title)
                _tabPagerAdapter.setTabName(controller)
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                if (icon == null)
                    controller.updateWebSiteIcon(null)
                else
                    controller.updateWebSiteIcon(icon)
                _tabPagerAdapter.setTabName(controller)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                controller.updateWebSiteIcon(null)
                _tabPagerAdapter.setTabName(controller)
                return false
            }

            @Deprecated("Deprecated in Java", ReplaceWith("false"))
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                controller.updateWebSiteIcon(null)
                _tabPagerAdapter.setTabName(controller)
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                _tabPagerAdapter.setUrl(url ?: "")
            }
        }
        webView.settings.javaScriptEnabled = true

        if (webSite != null)
            controller.goToWebSite(webSite)

        return webView
    }
}
