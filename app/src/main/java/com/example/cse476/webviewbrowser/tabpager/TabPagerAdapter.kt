package com.example.cse476.webviewbrowser.tabpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cse476.webviewbrowser.MainActivity
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.controller.webview.WebViewController
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class TabPagerAdapter(
    fragmentActivity: MainActivity,
) : FragmentStateAdapter(fragmentActivity), ITabPagerAdapter {
    private val tabList: MutableList<WebViewController> = fragmentActivity.tabList
    private val tabLayout = fragmentActivity.findViewById<TabLayout>(R.id.tabLayout)
    private val textEdit = fragmentActivity.findViewById<TextInputEditText>(R.id.urlField)

    override fun getItemCount(): Int {
        return this.tabList.size
    }

    override fun getItemId(position: Int): Long {
        return this.tabList[position].fragmentId
    }

    override fun containsItem(itemId: Long): Boolean {
        return tabList.any { it.fragmentId == itemId }
    }

    override fun createFragment(position: Int): Fragment {
        return WebViewFragmentFactory.newWebViewFragment(this.tabList[position])
    }

    override fun createNewTab() {
        val addedIndex = tabList.size
        this.tabList.add(WebViewController())
        this.notifyItemInserted(addedIndex)
    }

    override fun setTabName(controller: WebViewController) {
        val index = this.tabList.indexOf(controller)
        if (index == -1)
            return

        val tab = this.tabLayout.getTabAt(index)
        tab?.text = this.tabList[index].tabName
    }

    override fun setUrl(url: String) {
        this.textEdit.setText(url)
    }

    override fun goToWebSiteTab(index: Int, site: String) {
        this.tabList[index].goToWebSite(site)
    }

    override fun closeTab(position: Int) {
        this.tabList.removeAt(position)
        this.notifyItemRemoved(position)
    }
}
