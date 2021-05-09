package com.meuus90.zzim.common.constant

import org.junit.Assert
import org.junit.Test


class AppConfigTest {
    @Test
    fun AppConfigTestTest() {
        Assert.assertEquals(AppConfig.keyFavorites, "favorites")
        Assert.assertEquals(AppConfig.shareLink, "https://meuus90.page.link")
        Assert.assertEquals(AppConfig.iosAppStoreID, "IOS_STORE_ID")
    }
}