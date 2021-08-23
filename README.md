# AdmobRemote
utility for admob remote configuration (admob available now), change unit id, check new update version, move playstore account

```

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        //fetch data from Config
        //build default values first
        RemoteConfigUtils.buildDefaultValues(this)
        RemoteConfigUtils.getFirebaseRemoteConfig(this) { isSuccess, data ->
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
```

<img src="https://raw.githubusercontent.com/Hendriyawan/AdmobRemote/master/ss_admob_remote.jpg" />
