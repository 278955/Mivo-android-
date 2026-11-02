import { LevelPlay } from 'capacitor-levelplay';

// Put your IronSource App Key here
const APP_KEY = 'YOUR_IRONSOURCE_APP_KEY';

export async function initAds() {
  await LevelPlay.init({ appKey: APP_KEY });
  console.log('LevelPlay initialized');
}

export async function showRewarded() {
  try {
    await LevelPlay.showRewardedAd({ adUnitId: 'YOUR_REWARDED_AD_UNIT_ID' });
  } catch (e) {
    console.log('Rewarded not ready', e);
  }
}

export async function showInterstitial() {
  try {
    await LevelPlay.showInterstitialAd({ adUnitId: 'YOUR_INTERSTITIAL_AD_UNIT_ID' });
  } catch (e) {
    console.log('Interstitial not ready', e);
  }
}
