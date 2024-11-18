package com.example.cse476.webviewbrowser.tabpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cse476.webviewbrowser.MainActivity
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentActivity
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class TabPagerAdapter(
    fragmentActivity: MainActivity,
) : FragmentStateAdapter(fragmentActivity), ITabPagerAdapter {
    override val tabListReadOnly: List<WebViewFragmentActivity>
        get() {
            return tabList
        }

    private val tabList: MutableList<WebViewFragmentActivity> = fragmentActivity.tabList
    private val tabLayout = fragmentActivity.findViewById<TabLayout>(R.id.tabLayout)
    private val textEdit = fragmentActivity.findViewById<TextInputEditText>(R.id.urlField)

    override fun getItemCount(): Int {
        return this.tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return this.tabList[position]
    }

    override fun getItemId(position: Int): Long {
        return tabList[position].arguments?.getInt(WebViewFragmentActivity.TAB_INDEX)?.toLong()
            ?: position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return tabList.any {
            it.arguments?.getInt(WebViewFragmentActivity.TAB_INDEX)?.toLong() == itemId
        }
    }

    override fun createNewTab() {
        val addedIndex = tabList.size
        this.tabList.add(WebViewFragmentFactory.newWebViewFragment(addedIndex))
        this.notifyItemInserted(addedIndex)
    }

    override fun setTabName(index: Int) {
        val tab = this.tabLayout.getTabAt(index)
        tab?.text = this.tabList[index].webViewController?.tabName
    }

    override fun setUrl(url: String) {
        this.textEdit.setText(url)
    }

    override fun goToWebSiteTab(index: Int, site: String) {
        this.tabList[index].webViewController?.goToWebSite(site)
    }

    override fun closeTab(position: Int) {
        this.tabList.removeAt(position)
        this.notifyItemRemoved(position)
    }
}
