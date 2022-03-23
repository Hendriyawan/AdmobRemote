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
    const val BASE_NAME = "base_name"
    //private var defaults = HashMap<String, Any>()
    private var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    /**
     * build default values configuration
     * @param context Context
     * @return HashMap<String, Any>() return a data from fetch
     */
    fun buildDefaultValues(context: Context, configs: HashMap<String, Any>): HashMap<String, Any> {
        return configs
    }

    /**
     * fetch data configuration
     */
    fun getFirebaseRemoteConfig(
        context: Context,
        configs: HashMap<String, Any>,
        listener: (Boolean, FirebaseRemoteConfig) -> Unit
    ) {
        val packageName = "${context.packageName}".replace(".", "_")
        val remoteSettings = FirebaseRemoteConfigSettings.Builder().apply {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 60 * 60
        }.build()
        remoteConfig.setConfigSettingsAsync(remoteSettings)
        remoteConfig.setDefaultsAsync(buildDefaultValues(context, configs))
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            val success = it.isSuccessful
            if (success) {
                //val updated = it.result
                listener(success, remoteConfig)
            }
        }
    }
}