package com.gdev.remote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.gdev.admobremoteconfig.BuildConfig.VERSION_CODE
import com.gdev.admobremoteconfig.utils.RemoteConfigUtils
import com.gdev.admobremoteconfig.utils.RemoteConfigUtils.AD_BANNER_ID
import com.gdev.admobremoteconfig.utils.RemoteConfigUtils.AD_INTERSTITIAL_ID
import com.gdev.admobremoteconfig.utils.RemoteConfigUtils.VERSION_NAME
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //fetch data from Config
        //build default values first
        val configs = HashMap<String, Any>()
        configs.apply {
            val packageName = "$packageName".replace(".", "_")
            put("$packageName"+ "_$AD_BANNER_ID", "ca-app-pub-3940256099942544/6300978111")
            put("$packageName"+"_$AD_INTERSTITIAL_ID", "ca-app-pub-3940256099942544/1033173712")
            //put("$packageName"+"_$HAS_MOVE_PLAY_STORE", false)
            //put("$packageName"+"_$NEW_PLAY_STORE_URL", "https://play.google.com/store/apps/details?id=${context.packageName}")
            put("$packageName" + "_$VERSION_NAME", "1.0")
            put("$packageName" + "_$VERSION_CODE", 1)
            put("BASE_NAME", "")
        }
        val defaultConfig = RemoteConfigUtils.buildDefaultValues(this, configs)
        RemoteConfigUtils.getFirebaseRemoteConfig(this, defaultConfig) { _, data ->
            /*val adView = AdView(this)
            adView.adSize = AdSize.LARGE_BANNER
            adView.adUnitId = data["${
                packageName.replace(".",
                    "_")
            }_${RemoteConfigUtils.AD_BANNER_ID}"].toString()
            adView.loadAd(AdRequest.Builder().build())
            findViewById<LinearLayoutCompat>(R.id.layout_ad_view).apply {
                addView(adView)
            }*/
        }
    }
}