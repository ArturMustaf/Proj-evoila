// @ts-ignore
/**
 * This guard throws a error whenever the core-module is imported in a diffrent sub module
 * this is to avoid sub dependencies
 */
export class EnsureModuleLoadedOnceGuard {
  constructor(targetModule: any) {
    if (targetModule) {
      throw new Error(`${targetModule.constructor.name} has already been loaded. Import this module in the AppModule only.`);
    }
  }
}
