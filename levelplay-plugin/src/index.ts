import { registerPlugin } from '@capacitor/core';
import type { LevelPlayPlugin } from './definitions';

const LevelPlay = registerPlugin<LevelPlayPlugin>('LevelPlay', {
  web: () => import('./web').then(m => new m.LevelPlayWeb()),
});

export * from './definitions';
export { LevelPlay };
