package com.example.cse476.webviewbrowser.controller.webview

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.webkit.WebView
import com.example.cse476.webviewbrowser.MainActivity

class WebViewController() : Parcelable {
    val tabName : SpannableString
        get() {
            if (this._icon == null || this._resources == null)
                return SpannableString(this._webSiteName)

            val span = SpannableString("  " + this._webSiteName)
            val icon = BitmapDrawable(this._resources, this._icon)
            val aspectRatio =
                icon.intrinsicWidth.toFloat() / icon.intrinsicHeight.toFloat()
            icon.setBounds(
                0,
                0,
                (MainActivity.textSize * aspectRatio).toInt(),
                MainActivity.textSize.toInt()
            )
            span.setSpan(
                ImageSpan(icon, ImageSpan.ALIGN_BOTTOM),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return span
        }

    // Attachable
    private var _webView : WebView? = null
    private var _resources: Resources? = null

    // Parcelable
    private var _webSiteName: String? = null
    private var _icon: Bitmap? = null

    constructor(parcel: Parcel) : this() {
        this._webSiteName = parcel.readString()
        this._icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcel.readParcelable(Bitmap::class.java.classLoader, Bitmap::class.java)
        } else {
            parcel.readParcelable(Bitmap::class.java.classLoader)
        }
    }

    fun attachResources(resources: Resources) {
        if (this._resources == null)
            this._resources = resources
    }

    fun attachWebView(webView: WebView) {
        if (this._webView == null)
            this._webView = webView
    }

    fun updateWebSiteName(name: String) {
        this._webSiteName = name
    }

    fun updateWebSiteIcon(icon: Bitmap?) {
        this._icon = icon
    }

    fun goToWebSite(url: String) {
        this._webView?.loadUrl(url)
    }

    fun pauseWebView() {
        this._webView?.onPause()
    }

    fun continueWebView() {
        this._webView?.onResume()
    }

    fun getUrl(): String {
        return this._webView?.url ?: ""
    }

    fun stopWebView() {
        this._webView?.stopLoading()
        this._webView?.removeAllViews()
        this._webView?.destroy()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_webSiteName)
        parcel.writeParcelable(this._icon, flags)
    }

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
