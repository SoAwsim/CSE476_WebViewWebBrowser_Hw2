package com.example.cse476.webviewbrowser.WebViewFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cse476.webviewbrowser.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val WEB_SITE_NAME = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewFragmentView.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebViewFragmentView : Fragment() {
    var WebSiteName: String? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            WebSiteName = it.getString(WEB_SITE_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

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
        fun newInstance(param1: String) =
            WebViewFragmentView().apply {
                arguments = Bundle().apply {
                    putString(WEB_SITE_NAME, param1)
                }
            }
    }
}