package com.example.cse476.webviewbrowser.controller.webview

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.Log
import android.webkit.WebView
import com.example.cse476.webviewbrowser.MainActivity

class WebViewController() : Parcelable {
    val tabName : SpannableString
        get() {
            if (this._webSiteName == null)
                return SpannableString("New Tab")

            if (this._icon == null || this._resources == null)
                return SpannableString(this._webSiteName)

            val spanWithIcon = SpannableString("  " + this._webSiteName)
            val icon = BitmapDrawable(this._resources, this._icon)
            val aspectRatio =
                icon.intrinsicWidth.toFloat() / icon.intrinsicHeight.toFloat()
            icon.setBounds(
                0,
                0,
                (MainActivity.textSize * aspectRatio).toInt(),
                MainActivity.textSize.toInt()
            )
            spanWithIcon.setSpan(
                ImageSpan(icon, ImageSpan.ALIGN_BOTTOM),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spanWithIcon
        }
    // Related fragment's id
    var fragmentId: Long = System.currentTimeMillis()
        private set

    // Parcelable
    private var _webSiteName: String? = null
    private var _icon: Bitmap? = null
    private var _webViewBundle: Bundle? = null

    // Attachable
    private var webView : WebView? = null
    private var _resources: Resources? = null

    constructor(parcel: Parcel): this() {
        this.fragmentId = parcel.readLong()
        this._webSiteName = parcel.readString()

        this._icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcel.readParcelable(Bitmap::class.java.classLoader, Bitmap::class.java)
        } else {
            parcel.readParcelable(Bitmap::class.java.classLoader)
        }

        this._webViewBundle = parcel.readBundle(Bundle::class.java.classLoader)
    }

    fun attachResources(resources: Resources) {
        if (this._resources == null)
            this._resources = resources
    }

    // Returns whether given WebView was attached
    fun attachWebView(webView: WebView): Boolean {
        if (this.webView != null)
            return false

        this.webView = webView

        if (this._webViewBundle == null)
            return true

        try {
            this.webView?.restoreState(this._webViewBundle!!)
        } catch (e: Exception) {
            // If restore somehow fails set controller like a new tab
            Log.w("WebViewController", "attachWebView: restoring WebView state failed", e)
            this._webSiteName = null
            this._icon = null
        }
        this._webViewBundle = null
        return true
    }

    fun updateWebSiteName(name: String?) {
        this._webSiteName = name
    }

    fun updateWebSiteIcon(icon: Bitmap?) {
        this._icon = icon
    }

    fun goToWebSite(url: String) {
        this.webView?.loadUrl(url)
    }

    fun pauseWebView() {
        this.webView?.onPause()
    }

    fun resumeWebView() {
        this.webView?.onResume()
    }

    fun getUrl(): String {
        return this.webView?.url ?: ""
    }

    fun stopWebView() {
        this.webView?.stopLoading()
        this.webView?.removeAllViews()
        this.webView?.destroy()
        this.webView = null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(this.fragmentId)
        parcel.writeString(this._webSiteName)
        parcel.writeParcelable(this._icon, flags)

        this._webViewBundle = Bundle()
        try {
            this.webView?.saveState(this._webViewBundle!!)
        } catch (e: Exception) {
            Log.w("WebViewController", "writeToParcel: Saving WebView state failed", e)
        }
        parcel.writeBundle(this._webViewBundle)
    }

    // Parcelable requires me to override it irrelevant for my use case
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebViewController> {
        override fun createFromParcel(parcel: Parcel): WebViewController {
            return WebViewController(parcel)
        }

        override fun newArray(size: Int): Array<WebViewController?> {
            return arrayOfNulls(size)
        }
    }
}
