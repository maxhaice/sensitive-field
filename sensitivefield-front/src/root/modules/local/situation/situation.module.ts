import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../environment/md.module';
import { MapModule } from '../../public/map/map.module';
import { SituationComponent } from './components/situation.component/situation.component';
import { SituationNavbarComponent } from './components/situation.component/situation.navbar/navbar.component';
import { NavbarTimeLineComponent } from './components/situation.component/situation.navbar/navbar.time-line/time-line.component';
import { TimeLineTabComponent } from './components/situation.component/situation.navbar/navbar.time-line/time-line.tab/tab.component';

@NgModule({
    declarations: [SituationComponent,SituationNavbarComponent, NavbarTimeLineComponent,TimeLineTabComponent],
    imports: [FormsModule, MaterialModule, CommonModule, MapModule],
    bootstrap: [SituationComponent,SituationNavbarComponent, NavbarTimeLineComponent]
})
export class SituationModule {}