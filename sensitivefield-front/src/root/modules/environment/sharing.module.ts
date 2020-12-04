import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DefaultModule } from '../local/default/default.module';
import { LoginModule } from '../local/login/login.module';
import { SituationModule } from '../local/situation/situation.module';
import { MaterialModule } from './md.module';
import { StatisticModule } from '../local/statistic/statistic.module';
import { HistoryModule } from '../local/history/history.module';
import { SensorsModule } from '../local/sensors/sensors.module';
import { SenttingsModule } from '../local/settings/settings.module';
import {FormsModule} from '@angular/forms';

@NgModule({
    exports: [
      HttpClientModule, CommonModule, BrowserAnimationsModule, DefaultModule, HistoryModule, SenttingsModule,
      LoginModule, SituationModule, MaterialModule, StatisticModule, SensorsModule, FormsModule
    ]
})
export class SharingModule {}
 // TODO: import { ToastrModule } from 'ngx-toastr';
