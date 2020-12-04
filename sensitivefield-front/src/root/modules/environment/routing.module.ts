import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultComponent } from '../local/default/components/default.component/default.component';
import { HistoryComponent } from '../local/history/components/history.component/history.component';
import { LoginComponent } from '../local/login/components/login.component/login.component';
import { SensorsComponent } from '../local/sensors/components/sensors.component/sensors.component';
import { SettingsComponent } from '../local/settings/components/settings.component/settings.component';
import { SituationComponent } from '../local/situation/components/situation.component/situation.component';
import { StatisticComponent } from '../local/statistic/components/statistic.component/statistic.component';

const appRoutes: Routes =
[
  { path: '', component: LoginComponent},
  { path: 'situation', component: SituationComponent},
  { path: 'statistic', component: StatisticComponent},
  { path: 'history', component: HistoryComponent},
  { path: 'sensors', component: SensorsComponent},
  { path: 'settings', component: SettingsComponent},
  { path: '**', component: DefaultComponent }
];

@NgModule({ 
  imports: [
             RouterModule.forRoot(appRoutes),
  ],
  exports: [ 
      RouterModule
  ] 
})
export class RoutingModule {}
