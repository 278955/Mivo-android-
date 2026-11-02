export interface LevelPlayPlugin {
  getStatus(): Promise<{ integrated: boolean; message: string }>;
  initialize(options: { appKey: string }): Promise<void>;
  showRewardedAd(): Promise<{ rewarded: boolean }>;
  showInterstitial(): Promise<void>;
}
