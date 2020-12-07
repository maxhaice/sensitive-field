import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { CoreModule } from './modules/environment/core.module';
import { RoutingModule } from './modules/environment/routing.module';
import { SharingModule } from './modules/environment/sharing.module';
import { RootComponent } from './modules/root/root.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './modules/root/navbar/navbar.component';
import {CommonModule} from '@angular/common';

@NgModule({
    declarations: [RootComponent, NavbarComponent],
    imports: [ BrowserModule, CommonModule, CoreModule, SharingModule, RoutingModule, BrowserAnimationsModule ],
    bootstrap: [RootComponent]
})
export class RootModule {}
