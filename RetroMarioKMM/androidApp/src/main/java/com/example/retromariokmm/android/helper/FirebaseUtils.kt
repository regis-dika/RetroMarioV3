package com.example.retromariokmm.android.helper

import android.net.Uri
import com.example.retromariokmm.utils.BASE_URL
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters

fun generateSharingLink(
    deepLink: Uri,
    getShareableLink: (String) -> Unit = {},
) {
    val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink().run {
        // What is this link parameter? You will get to know when we will actually use this function.
        link = deepLink

        // [domainUriPrefix] will be the domain name you added when setting up Dynamic Links at Firebase Console.
        // You can find it in the Dynamic Links dashboard.
        domainUriPrefix = BASE_URL

        // Required
        androidParameters {
            build()
        }

        // Finally
        buildDynamicLink()
    }

    // Pass the newly created dynamic link so that we retrieve and use it further for sharing via Intent.
    getShareableLink.invoke(dynamicLink.uri.toString())
}