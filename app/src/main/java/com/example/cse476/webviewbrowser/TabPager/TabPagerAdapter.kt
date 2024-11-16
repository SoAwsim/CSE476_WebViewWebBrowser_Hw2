package com.example.cse476.webviewbrowser.TabPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cse476.webviewbrowser.WebViewFragment.WebViewFragmentFactory
import com.example.cse476.webviewbrowser.WebViewFragment.WebViewFragmentView

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity), ITabPagerAdapter {
    private val tabList: MutableList<WebViewFragmentView> =
        mutableListOf(WebViewFragmentFactory.NewWebViewFragment())

    val tabListReadOnly: List<WebViewFragmentView>
        get() {
            return tabList
        }

    override fun getItemCount(): Int {
        return this.tabList.count()
    }

    override fun createFragment(position: Int): Fragment {
        return this.tabList[position];
    }

    override fun createNewTab() {
        this.tabList.add(WebViewFragmentFactory.NewWebViewFragment())
        this.notifyItemInserted(tabList.count() - 1);
    }

    override fun closeTab(position: Int) {
        TODO("Not yet implemented")
    }
}
