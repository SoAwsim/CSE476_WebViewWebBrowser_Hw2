package com.example.cse476.webviewbrowser.webbiewfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.controller.webview.WebViewController
import com.example.cse476.webviewbrowser.controller.webview.WebViewControllerFactory
import com.example.cse476.webviewbrowser.tabpager.ITabPagerAdapter

const val TAB_INDEX = "tab_index"

class WebViewFragmentActivity(tabPagerAdapter: ITabPagerAdapter) : Fragment() {
    private val _tabPagerAdapter = tabPagerAdapter
    private var _index: Int = -1

    var webViewController: WebViewController? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            this._index = it!!.getInt(TAB_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = this.requireView().findViewById<WebView>(R.id.webView)
        this.webViewController = WebViewControllerFactory.newWebViewController(
            webView,
            this._index,
            this._tabPagerAdapter,
            this.requireContext()
        )
    }
}