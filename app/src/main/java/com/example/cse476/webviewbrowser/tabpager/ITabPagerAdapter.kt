package com.example.cse476.webviewbrowser.tabpager

import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentActivity

interface ITabPagerAdapter {
    val tabListReadOnly: List<WebViewFragmentActivity>

    fun createNewTab()
    fun closeTab(position: Int)
    fun setTabName(index: Int)
    fun goToWebSiteTab(index: Int, site: String)
    fun setUrl(url: String)
}