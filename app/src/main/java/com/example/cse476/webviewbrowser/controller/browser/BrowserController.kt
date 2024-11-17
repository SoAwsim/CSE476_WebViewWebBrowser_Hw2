package com.example.cse476.webviewbrowser.controller.browser

import com.example.cse476.webviewbrowser.tabpager.ITabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class BrowserController(tabPager: ITabPagerAdapter, tabLayout: TabLayout, text: TextInputEditText) {
    private val _tabPagerAdapter: ITabPagerAdapter = tabPager
    private val _tabLayout: TabLayout = tabLayout
    private val _textInput: TextInputEditText = text

    fun createNewTab() {
        _tabPagerAdapter.createNewTab()
    }

    fun goToWebSite() {
        val index = this._tabLayout.selectedTabPosition
        this._tabPagerAdapter.goToWebSiteTab(index, this._textInput.text.toString())
    }

    fun setUrlBar(url: String) {
        this._textInput.setText(url)
    }
}