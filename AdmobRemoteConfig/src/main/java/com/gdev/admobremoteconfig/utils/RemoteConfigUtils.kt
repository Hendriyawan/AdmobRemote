package com.gdev.admobremoteconfig.utils

import android.content.Context
import android.util.Log.d
import com.gdev.admobremoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfigUtils {
    const val TAG = "RemoteConfigUtils"
    const val AD_BANNER_ID = "ad_banner_id"
    const val AD_INTERSTITIAL_ID = "ad_interstitial_id"
    const val HAS_MOVE_PLAY_STORE = "has_move_playstore"
    const val NEW_PLAY_STORE_URL = "new_play_store_url"
    const val VERSION_NAME = "version_name"
    const val VERSION_CODE = "version_code"
    private var defaults = HashMap<String, Any>()
    private var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    /**
     * build default values configuration
     * @param context Context
     * @return HashMap<String, Any>() return a data from fetch
     */
    fun buildDefaultValues(context: Context): HashMap<String, Any> {
        return defaults.apply {
            val packageName = "${context.packageName}".replace(".", "_")
            put("$packageName"+ "_$AD_BANNER_ID", "ca-app-pub-3940256099942544/6300978111")
            put("$packageName"+"_$AD_INTERSTITIAL_ID", "ca-app-pub-3940256099942544/1033173712")
            put("$packageName"+"_$HAS_MOVE_PLAY_STORE", false)
            put("$packageName"+"_$NEW_PLAY_STORE_URL", "https://play.google.com/store/apps/details?id=${context.packageName}")
            put("$packageName"+"_$VERSION_NAME", "1.0")
            put("$packageName"+"_$VERSION_CODE", 1)
        }
    }

    /**
     * fetch data configuration
     */
    fun getFirebaseRemoteConfig(
        context: Context,
        listener: (Boolean, HashMap<String, Any>) -> Unit
    ) {
        val packageName = "${context.packageName}".replace(".", "_")
        val remoteSettings = FirebaseRemoteConfigSettings.Builder().apply {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 60 * 60
        }.build()
        remoteConfig.setConfigSettingsAsync(remoteSettings)
        remoteConfig.setDefaultsAsync(buildDefaultValues(context))
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            val success = it.isSuccessful
            if (success) {
                //val updated = it.result
                val fetched = HashMap<String, Any>()
                fetched["$packageName"+"_$AD_BANNER_ID"] = remoteConfig.getString("$packageName"+"_$AD_BANNER_ID")
                fetched["$packageName"+"_$AD_INTERSTITIAL_ID"] = remoteConfig.getString("$packageName"+"_$AD_INTERSTITIAL_ID")
                fetched["$packageName"+"_$HAS_MOVE_PLAY_STORE"] = remoteConfig.getBoolean("$packageName"+"_$HAS_MOVE_PLAY_STORE")
                fetched["$packageName"+"_$NEW_PLAY_STORE_URL"] = remoteConfig.getString("$packageName"+"_$NEW_PLAY_STORE_URL")
                d(TAG, "$fetched")
                listener(success, fetched)
            }
        }
    }
}