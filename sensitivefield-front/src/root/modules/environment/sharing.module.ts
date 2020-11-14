import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DefaultModule } from '../local/default/default.module';
import { PublicModule } from '../public/public.module';
import { LoginModule } from '../local/login/login.module';
import { SituationModule } from '../local/situation/situation.module';
import { MaterialModule } from './md.module';

@NgModule({
    exports: [
      HttpClientModule, CommonModule, BrowserAnimationsModule, DefaultModule,
      PublicModule, LoginModule, SituationModule, MaterialModule,
    ]
})
export class SharingModule {}
