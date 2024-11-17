package com.example.cse476.webviewbrowser.tabpager

import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cse476.webviewbrowser.R
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentActivity
import com.example.cse476.webviewbrowser.webbiewfragment.WebViewFragmentFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity), ITabPagerAdapter {
    private val tabList: MutableList<WebViewFragmentActivity> =
        mutableListOf(WebViewFragmentFactory.NewWebViewFragment(0, this))
    private val tabLayout = fragmentActivity.findViewById<TabLayout>(R.id.tabLayout)
    private val textSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        16f,
        fragmentActivity.resources.displayMetrics
    )
    private val textEdit = fragmentActivity.findViewById<TextInputEditText>(R.id.urlField)

    override val tabListReadOnly: List<WebViewFragmentActivity>
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
        val addedIndex = tabList.count()
        this.tabList.add(WebViewFragmentFactory.NewWebViewFragment(addedIndex, this))
        this.notifyItemInserted(addedIndex);
    }

    override fun setTabName(index: Int) {
        val tab = this.tabLayout.getTabAt(index)
        val controller = this.tabList[index].webViewController
        val webSiteName: SpannableString
        if (controller?.icon != null) {
            webSiteName = SpannableString("  " + controller.webSiteName)
            val icon: Drawable = controller.icon!!
            val aspectRatio = icon.intrinsicWidth.toFloat() / icon.intrinsicHeight
            icon.setBounds(0, 0, (textSize * aspectRatio).toInt(), textSize.toInt())
            webSiteName.setSpan(
                ImageSpan(icon, ImageSpan.ALIGN_BOTTOM),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        else {
            webSiteName = SpannableString(controller?.webSiteName)
        }

        tab?.text = webSiteName
    }



    override fun setIcon(index: Int) {
        val tab = this.tabLayout.getTabAt(index)
        tab?.icon = this.tabList[index].webViewController?.icon
    }

    override fun setUrl(url: String) {
        this.textEdit.setText(url)
    }

    override fun goToWebSiteTab(index: Int, site: String) {
        this.tabList[index].webViewController?.goToWebSite(site)
    }

    override fun closeTab(position: Int) {
        TODO("Not yet implemented")
    }
}
