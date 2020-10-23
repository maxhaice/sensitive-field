import { BrowserModule } from '@angular/platform-browser';
import { NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {Routes, RouterModule} from '@angular/router';
import { ChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { Router } from './components/_routs/_router/_router.component';
import { SituationRout } from './components/_routs/situation/situation.component';
import { NavbarComponent } from './components/_components/navbar/navbar.component';
import { MapComponent } from './components/_components/map/map.component';
import { SituationNavbarComponent } from './components/_components/situation.navbar/navbar.component';
import { StatisticRout } from './components/_routs/statistic/statistic.component';
import { SensorsRout } from './components/_routs/sensors/system.component';
import { HistoryRout } from './components/_routs/history/history.component';
import { SystemRout } from './components/_routs/system/system.component';
import { LoginRoute } from './components/_routs/login/login.component';
import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DesignSetComponent} from './components/_components/design-set/design-set.component'
import {FilterStatComponent} from './components/_components/filter-stat/filter-stat.component'
import {SoundSetComponent} from './components/_components/sound-set/sound-set.component';
import {RegisterComponent} from './components/_components/register/register.component';
import { LowComponent } from './components/_components/low-poly/low.component';
import {MaterialModule} from './material.design';
import { ProfileComponent } from './components/_components/profile/profile.component';

const appRoutes: Routes =
[
  // { path: '', component: SituationRout},
  { path: '', component: LoginRoute},
  { path: 'situation', component: SituationRout},
  { path: 'statistic', component: StatisticRout},
  { path: 'history', component: HistoryRout},
  { path: 'sensors', component: SensorsRout},
  { path: 'system', component: SystemRout},
  // { path: '', redirectTo: 'auth'},
  { path: '**', redirectTo: 'situation'}
];

@NgModule({
    declarations: [Router, SituationRout, NavbarComponent, MapComponent,
        SituationNavbarComponent, StatisticRout, SensorsRout,ProfileComponent, FilterStatComponent,
        HistoryRout, SystemRout, LoginRoute, DesignSetComponent, RegisterComponent, LowComponent, SoundSetComponent
    ],
  imports: [ BrowserModule, RouterModule.forRoot(appRoutes), HttpClientModule,ReactiveFormsModule,
             RouterModule, CommonModule, ChartsModule, FormsModule, Ng2GoogleChartsModule,
             BrowserAnimationsModule, ToastrModule.forRoot(), MaterialModule

  ],
  bootstrap: [Router]
})
export class AppModule {}
