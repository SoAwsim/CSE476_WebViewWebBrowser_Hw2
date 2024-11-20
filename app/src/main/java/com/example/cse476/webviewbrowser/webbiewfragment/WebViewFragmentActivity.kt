package com.example.cse476.webviewbrowser.webbiewfragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.controller.webview.WebViewController
import com.example.cse476.webviewbrowser.controller.webview.CustomWebViewFactory

class WebViewFragmentActivity : Fragment() {
    companion object {
        const val TAB_ID = "tab_index"
        const val WEBSITE_NAME = "web_site_name"

        private const val CONTROLLER = "webview_controller"
    }
    private var _webViewController: WebViewController? = null

    fun attachController(controller: WebViewController) {
        if (this._webViewController == null)
            this._webViewController = controller
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this._webViewController != null)
            return

        this._webViewController = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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

        val webSite = savedInstanceState?.getString(WEBSITE_NAME)
        val webView = CustomWebViewFactory().newCustomWebView(
            this._webViewController!!,
            this.requireContext(),
            webSite
        )

        webViewContainer.addView(webView)
    }

    override fun onResume() {
        super.onResume()
        this._webViewController?.resumeWebView()
    }

    override fun onPause() {
        super.onPause()
        this._webViewController?.pauseWebView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(WEBSITE_NAME, this._webViewController?.getUrl())
        outState.putParcelable(CONTROLLER, _webViewController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._webViewController?.stopWebView()
    }
}
