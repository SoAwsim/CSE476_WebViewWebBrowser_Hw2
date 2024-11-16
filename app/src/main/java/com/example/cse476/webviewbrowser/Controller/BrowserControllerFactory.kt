package com.example.cse476.webviewbrowser.Controller

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.TabPager.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BrowserControllerFactory(activity: AppCompatActivity) {
    private val _activity = activity
    private lateinit var _browserController: BrowserController

    fun CreateBrowserController() {
        val viewPager2 = this._activity.findViewById<ViewPager2>(R.id.webViewPager)
        val tabLayout = this._activity.findViewById<TabLayout>(R.id.tabLayout)
        val adapter = TabPagerAdapter(this._activity)
        this._browserController = BrowserController(adapter)
        this.InitializeButtons()

        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = adapter.tabListReadOnly[position].WebSiteName ?: ("Tab " + (position + 1))
        }.attach()
    }

    private fun InitializeButtons() {
        val button = this._activity.findViewById<Button>(R.id.newTabButton)
        button.setOnClickListener{
            this._browserController.CreateNewTab()
        }
    }
}