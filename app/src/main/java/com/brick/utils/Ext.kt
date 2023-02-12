package com.brick.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by Loitp on 12,February,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
const val URL_POLICY_NOTION =
    "https://loitp.notion.site/loitp/Privacy-Policy-319b1cd8783942fa8923d2a3c9bce60f/"

fun Context.openBrowserPolicy(
) {
    this.openUrlInBrowser(url = URL_POLICY_NOTION)
}

fun Context?.openUrlInBrowser(
    url: String?
) {
    if (this == null || url.isNullOrEmpty()) {
        return
    }
    val defaultBrowser =
        Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
    defaultBrowser.data = Uri.parse(url)
    this.startActivity(defaultBrowser)
}
