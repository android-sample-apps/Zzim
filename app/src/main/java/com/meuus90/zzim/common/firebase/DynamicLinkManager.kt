package com.meuus90.zzim.common.firebase

import android.net.Uri
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.meuus90.zzim.common.constant.AppConfig
import timber.log.Timber

object DynamicLinkManager {
    fun makeDynamicLink(params: String, onSuccess: (uri: String) -> Unit) {
        val link = Uri.Builder()
            .scheme("https")
            .authority("zzim-b3a3a.firebaseapp.com")
            .appendQueryParameter(AppConfig.keyFavorites, params)
            .build()

        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(link)
            .setDomainUriPrefix(AppConfig.shareLink)
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder()
                    .build()
            )
//            .setIosParameters(
//                DynamicLink.IosParameters.Builder(BuildConfig.APPLICATION_ID)
//                    .setAppStoreId(AppConfig.iosAppStoreID)
//                    .build()
//            )
            .buildShortDynamicLink()
            .addOnSuccessListener { shortDynamicLink ->
                shortDynamicLink.shortLink?.let {
                    onSuccess(it.toString())
                }
            }
            .addOnFailureListener {
                Timber.e("Exception : $it")
            }
    }
}