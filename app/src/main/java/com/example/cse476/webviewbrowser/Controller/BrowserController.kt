package com.example.cse476.webviewbrowser.Controller

import com.example.cse476.webviewbrowser.TabPager.ITabPagerAdapter

class BrowserController(tabPager: ITabPagerAdapter) {
    private val _tabPagerAdapter: ITabPagerAdapter = tabPager

    fun CreateNewTab() {
        _tabPagerAdapter.createNewTab()
    }
}