package com.example.cse476.webviewbrowser.controller.browser

import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.cse476.webviewbrowser.MainActivity
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.controller.webview.WebViewControllerFactory
import com.example.cse476.webviewbrowser.tabpager.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText

class BrowserControllerFactory(activity: MainActivity) {
    private val _activity = activity
    private lateinit var _browserController: BrowserController

    fun createBrowserController() {
        val viewPager2 = this._activity.findViewById<ViewPager2>(R.id.webViewPager)
        val tabLayout = this._activity.findViewById<TabLayout>(R.id.tabLayout)
        val text = this._activity.findViewById<TextInputEditText>(R.id.urlField)
        val adapter = TabPagerAdapter(this._activity)
        WebViewControllerFactory.setTabPager(adapter)
        this._browserController = BrowserController(adapter, tabLayout, text)
        this.initializeButtons()

        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = adapter.tabListReadOnly[position].webViewController?.tabName
                ?: ("Tab " + (position + 1))
        }.attach()
    }

    private fun initializeButtons() {
        var button: Button = this._activity.findViewById(R.id.newTabButton)
        button.setOnClickListener{
            this._browserController.createNewTab()
        }

        button = this._activity.findViewById(R.id.goButton)
        button.setOnClickListener{
            this._browserController.goToWebSite()
        }
    }
}