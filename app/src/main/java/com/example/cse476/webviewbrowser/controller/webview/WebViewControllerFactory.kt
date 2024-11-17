package com.example.cse476.webviewbrowser.controller.webview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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

        @JvmStatic
        fun newWebViewController(
            webView: WebView,
            index: Int,
            context: Context,
            webSite: String? = null
        ): WebViewController {
            val controller = WebViewController(webView, index)

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
                        controller.updateWebSiteIcon(BitmapDrawable(context.resources, icon))
                    _tabPagerAdapter!!.setTabName(index)
                }
            }
            webView.webViewClient = object : WebViewClient(){
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }

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

            if (webSite != null)
                controller.goToWebSite(webSite)

            return controller
        }
    }
}
