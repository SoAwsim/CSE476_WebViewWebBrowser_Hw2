package com.example.cse476.webviewbrowser.tabpager

import com.example.cse476.webviewbrowser.controller.webview.WebViewController

interface ITabPagerAdapter {
    fun createNewTab()
    fun closeTab(position: Int)
    fun setTabName(controller: WebViewController)
    fun goToWebSiteTab(index: Int, site: String)
    fun setUrl(url: String)
}