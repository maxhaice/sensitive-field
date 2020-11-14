import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultComponent } from '../local/default/components/default.component/default.component';
import { LoginComponent } from '../local/login/components/login.component/login.component';
import { SituationComponent } from '../local/situation/components/situation.component/situation.component';

const appRoutes: Routes =
[
  { path: '', component: LoginComponent},
  { path: 'situation', component: SituationComponent},
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
