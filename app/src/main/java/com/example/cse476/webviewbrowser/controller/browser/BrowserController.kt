package com.example.cse476.webviewbrowser.controller.browser

import android.content.Context
import android.widget.Toast
import com.example.cse476.webviewbrowser.tabpager.ITabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class BrowserController(
    tabPager: ITabPagerAdapter,
    tabLayout: TabLayout,
    text: TextInputEditText,
    context: Context
) {
    private val _tabPagerAdapter = tabPager
    private val _tabLayout = tabLayout
    private val _textInput = text
    private val _context = context

    fun createNewTab() {
        _tabPagerAdapter.createNewTab()
    }

    fun goToWebSite() {
        val index = this._tabLayout.selectedTabPosition
        if (index < 0) {
            Toast.makeText(this._context, "Create a new tab!", Toast.LENGTH_SHORT).show()
            return
        }

        this._tabPagerAdapter.goToWebSiteTab(index, this._textInput.text.toString())
    }
}