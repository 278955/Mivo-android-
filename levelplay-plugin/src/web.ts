import { WebPlugin } from '@capacitor/core';
import type { LevelPlayPlugin } from './definitions';

export class LevelPlayWeb extends WebPlugin implements LevelPlayPlugin {
  async getStatus(): Promise<{ integrated: boolean; message: string }> {
    return { integrated: false, message: 'Web not supported - use Android' };
  }
  async initialize(): Promise<void> {
    console.log('LevelPlay: Web init skipped');
  }
  async showRewardedAd(): Promise<{ rewarded: boolean }> {
    return { rewarded: false };
  }
  async showInterstitial(): Promise<void> {
    return;
  }
}
