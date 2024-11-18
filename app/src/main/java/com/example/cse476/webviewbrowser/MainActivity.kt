package com.example.cse476.webviewbrowser

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cse476.webviewbrowser.controller.browser.BrowserControllerFactory
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentActivity
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentFactory

class MainActivity : AppCompatActivity() {
    companion object {
        var textSize: Float = 16f
            private set

        private const val TAB = "TAB_COUNT"
    }

    var tabList: MutableList<WebViewFragmentActivity> = mutableListOf()
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            val indices = savedInstanceState.getIntegerArrayList(TAB) ?: listOf()
            tabList = indices.map { index ->
                supportFragmentManager.findFragmentByTag("f$index") as? WebViewFragmentActivity
                    ?: WebViewFragmentFactory.newWebViewFragment(index)
            }.toMutableList()
        } else {
            tabList.add(WebViewFragmentFactory.newWebViewFragment(0))
        }

        // Set correct text size for tab names, used for web site icons
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            16f,
            this.resources.displayMetrics
        )

        BrowserControllerFactory(this).createBrowserController()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val indices = tabList.map { it.arguments?.getInt(WebViewFragmentActivity.TAB_INDEX) ?: 0 } as ArrayList
        outState.putIntegerArrayList(TAB, indices)
    }
}
