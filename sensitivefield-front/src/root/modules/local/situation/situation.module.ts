import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../environment/md.module';
import { MapModule } from '../../public/map/map.module';
import { SituationComponent } from './components/situation.component/situation.component';
import { SituationNavbarComponent } from './components/situation.component/situation.navbar/navbar.component';
import { SituationSideBarComponent } from './components/situation.component/situation.sidebar/sidebar.component';

@NgModule({
    declarations: [SituationComponent,SituationNavbarComponent, SituationSideBarComponent],
    imports: [FormsModule, MaterialModule, CommonModule, MapModule],
    bootstrap: [SituationComponent,SituationNavbarComponent]
})
export class SituationModule {}