import { ModuleWithProviders, NgModule } from '@angular/core';

@NgModule({
    declarations: [],
    imports: []
})
export class CoreModule {
    static forRoot(): ModuleWithProviders<CoreModule> {
        return {
          ngModule: CoreModule,
          providers: [
            // AuthService,
            // LoggerService,
            // SettingsService,
          ],
        }
      }
}
