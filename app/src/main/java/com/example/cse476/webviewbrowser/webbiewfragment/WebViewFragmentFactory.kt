package com.example.cse476.webviewbrowser.webbiewfragment

import com.example.cse476.webviewbrowser.controller.webview.WebViewController

class WebViewFragmentFactory {
    companion object {

        @JvmStatic
        fun newWebViewFragment(controller: WebViewController): WebViewFragmentActivity {
            val fragment = WebViewFragmentActivity()
            fragment.attachController(controller)
            return fragment
        }
    }
}
