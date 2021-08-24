package com.gdev.remote

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.gdev.admobremoteconfig.utils.RemoteConfigUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //fetch data from Config
        //build default values first
        RemoteConfigUtils.buildDefaultValues(this)
        RemoteConfigUtils.getFirebaseRemoteConfig(this) { _, data ->
            val adView = AdView(this)
            adView.adSize = AdSize.LARGE_BANNER
            adView.adUnitId = data["${packageName.replace(".", "_")}_${RemoteConfigUtils.AD_BANNER_ID}"].toString()
            adView.loadAd(AdRequest.Builder().build())
            findViewById<LinearLayoutCompat>(R.id.layout_ad_view).apply {
                addView(adView)
            }
        }
    }
}