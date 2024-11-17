package com.example.cse476.webviewbrowser.webbiewfragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.controller.webview.WebViewController
import com.example.cse476.webviewbrowser.controller.webview.WebViewControllerFactory

const val TAB_INDEX = "tab_index"
const val WEBSITE_NAME = "web_site_name"
private const val CONTROLLER = "webview_controller"
const val WEBVIEW_STATE = "webview_state"

class WebViewFragmentActivity : Fragment() {
    var index: Int = -1
        private set

    var webViewController: WebViewController? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            this.index = it!!.getInt(TAB_INDEX)
        }
        this.index = savedInstanceState?.getInt(TAB_INDEX) ?: this.index
        this.webViewController = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getParcelable(CONTROLLER, WebViewController::class.java)
        } else {
            savedInstanceState?.getParcelable(CONTROLLER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webViewContainer = this.requireView().findViewById<FrameLayout>(R.id.webViewContainer)
        webViewContainer.removeAllViews()
        val webView = this.webViewController?._webView ?: WebView(this.requireContext())
        val factory = WebViewControllerFactory()

        val webSite = savedInstanceState?.getString(WEBSITE_NAME)
        val webViewState = savedInstanceState?.getBundle(WEBVIEW_STATE)
        if (this.webViewController == null) {
            this.webViewController = factory.newWebViewController(
                webView,
                this.index,
                this.requireContext(),
                webSite,
                webViewState
            )
        } else {
            factory.setupController(
                this.webViewController!!,
                webView,
                this.index,
                this.requireContext(),
                webSite,
                webViewState
            )
        }
        webViewContainer.addView(webView)
    }

    override fun onResume() {
        super.onResume()
        this.webViewController?.resumeWebView()
    }

    override fun onPause() {
        super.onPause()
        this.webViewController?.pauseWebView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_INDEX, this.index)
        outState.putString(WEBSITE_NAME, this.webViewController?.getUrl())
        outState.putParcelable(CONTROLLER, webViewController)

        val webViewState = Bundle()
        this.webViewController?._webView?.saveState(webViewState)
        outState.putBundle(WEBVIEW_STATE, webViewState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.webViewController?.stopWebView()
    }
}
