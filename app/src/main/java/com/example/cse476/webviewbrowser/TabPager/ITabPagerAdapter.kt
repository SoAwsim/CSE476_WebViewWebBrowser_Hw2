package com.example.cse476.webviewbrowser.TabPager

interface ITabPagerAdapter {
    fun createNewTab()
    fun closeTab(position: Int)
}