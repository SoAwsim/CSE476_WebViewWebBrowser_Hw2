package com.example.cse476.webviewbrowser.webbiewfragment

import android.os.Bundle

class WebViewFragmentFactory {
    companion object {
        @JvmStatic
        fun NewWebViewFragment(index: Int) =
            WebViewFragmentActivity().apply {
                arguments = Bundle().apply {
                    putInt(TAB_INDEX, index)
                }
            }
    }
}
