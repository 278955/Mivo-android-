package com.mivo.levelplay;

import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener;
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;

@CapacitorPlugin(name = "LevelPlay")
public class LevelPlayPlugin extends Plugin {

    private boolean isInitialized = false;
    private boolean rewardedReady = false;
    private boolean interstitialReady = false;

    @PluginMethod
    public void getStatus(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("integrated", true);
        ret.put("message", isInitialized ? "LevelPlay ready" : "LevelPlay installed, not initialized");
        call.resolve(ret);
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        String appKey = call.getString("appKey");
        if (appKey == null) {
            call.reject("appKey is required");
            return;
        }

        getActivity().runOnUiThread(() -> {
            try {
                // Init IronSource LevelPlay
                IronSource.setLevelPlayInterstitialListener(new LevelPlayInterstitialListener() {
                    @Override
                    public void onAdReady(AdInfo adInfo) {
                        interstitialReady = true;
                        Log.d("LevelPlay", "Interstitial Ready");
                    }
                    @Override
                    public void onAdLoadFailed(IronSourceError error) {
                        interstitialReady = false;
                        Log.e("LevelPlay", "Interstitial load failed: " + error.getErrorMessage());
                    }
                    @Override
                    public void onAdOpened(AdInfo adInfo) {}
                    @Override
                    public void onAdShowFailed(IronSourceError error, AdInfo adInfo) {}
                    @Override
                    public void onAdClicked(AdInfo adInfo) {}
                    @Override
                    public void onAdClosed(AdInfo adInfo) {
                        // Auto reload
                        IronSource.loadLevelPlayInterstitial();
                    }
                });

                IronSource.setLevelPlayRewardedVideoListener(new LevelPlayRewardedVideoListener() {
                    @Override
                    public void onAdAvailable(AdInfo adInfo) {
                        rewardedReady = true;
                    }
                    @Override
                    public void onAdUnavailable() {
                        rewardedReady = false;
                    }
                    @Override
                    public void onAdOpened(AdInfo adInfo) {}
                    @Override
                    public void onAdClosed(AdInfo adInfo) {
                        rewardedReady = false;
                        IronSource.loadLevelPlayRewardedVideo();
                    }
                    @Override
                    public void onAdRewarded(com.ironsource.mediationsdk.model.Placement placement, AdInfo adInfo) {}
                    @Override
                    public void onAdShowFailed(IronSourceError error, AdInfo adInfo) {}
                    @Override
                    public void onAdClicked(AdInfo adInfo) {}
                });

                IronSource.init(getActivity(), appKey);
                IronSource.loadLevelPlayInterstitial();
                IronSource.loadLevelPlayRewardedVideo();

                isInitialized = true;
                JSObject ret = new JSObject();
                ret.put("success", true);
                call.resolve(ret);

            } catch (Exception e) {
                call.reject("Init failed: " + e.getMessage());
            }
        });
    }

    @PluginMethod
    public void showRewardedAd(PluginCall call) {
        if (!IronSource.isRewardedVideoAvailable()) {
            call.reject("Rewarded not ready");
            return;
        }
        getActivity().runOnUiThread(() -> {
            IronSource.showRewardedVideo();
            JSObject ret = new JSObject();
            ret.put("rewarded", true);
            call.resolve(ret);
        });
    }

    @PluginMethod
    public void showInterstitial(PluginCall call) {
        if (!IronSource.isLevelPlayInterstitialReady()) {
            call.reject("Interstitial not ready");
            return;
        }
        getActivity().runOnUiThread(() -> {
            IronSource.showLevelPlayInterstitial();
            call.resolve();
        });
    }
}
