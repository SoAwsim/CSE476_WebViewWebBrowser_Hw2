package com.example.cse476.webviewbrowser.controller.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cse476.webviewbrowser.tabpager.ITabPagerAdapter

class WebViewControllerFactory {
    companion object{
        private var _tabPagerAdapter: ITabPagerAdapter? = null

        @JvmStatic
        fun setTabPager(pager: ITabPagerAdapter) {
            if (_tabPagerAdapter != null)
                return

            _tabPagerAdapter = pager
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun newWebViewController(
        webView: WebView,
        index: Int,
        context: Context,
        webSite: String? = null,
        webViewState: Bundle? = null
    ): WebViewController {
        val controller = WebViewController()
        controller.updateWebSiteName("Tab " + (index + 1))

        this.setupController(
            controller,
            webView,
            index,
            context,
            webSite,
            webViewState
        )

        return controller
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupController(
        controller: WebViewController,
        webView: WebView,
        index: Int,
        context: Context,
        webSite: String? = null,
        webViewState: Bundle? = null
    ) {
        controller.attachResources(context.resources)
        controller.attachWebView(webView)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                controller.updateWebSiteName(title ?: ("Tab " + (index + 1)))
                _tabPagerAdapter!!.setTabName(index)
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                if (icon == null)
                    controller.updateWebSiteIcon(null)
                else
                    controller.updateWebSiteIcon(icon)
                _tabPagerAdapter!!.setTabName(index)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            @Deprecated("Deprecated in Java", ReplaceWith("false"))
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                _tabPagerAdapter!!.setUrl(url ?: "")
            }
        }
        webView.settings.javaScriptEnabled = true
        if (webViewState != null)
            webView.restoreState(webViewState)

        if (webSite != null)
            controller.goToWebSite(webSite)
    }
}
