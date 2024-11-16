package com.example.cse476.webviewbrowser.WebViewFragment

import android.os.Bundle

class WebViewFragmentFactory {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebViewFragmentView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun NewWebViewFragment(param1: String? = null) =
            WebViewFragmentView().apply {
                arguments = Bundle().apply {
                    putString(WEB_SITE_NAME, param1)
                }
            }
    }
}