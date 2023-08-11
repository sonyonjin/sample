package kr.mediascope.smartsing.demo.common.extension

import android.content.Context


/**
 * Context extensions
 * @author mjkim
 * @since 1.0.0
 */

/**
 * StatusBar 높이값(px) 반환
 */
fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

/**
 * NavigationBar 높이값(px) 반환
 */
fun Context.navigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}