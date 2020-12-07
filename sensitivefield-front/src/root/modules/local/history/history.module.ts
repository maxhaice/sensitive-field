import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { MaterialModule } from '../../environment/md.module';
import { MapModule } from '../../public/map/map.module';
import { HistoryComponent } from './components/history.component/history.component';
import { HistoryTimeLineComponent } from './components/history.timeline.component/timeline.component';

@NgModule({
  declarations: [HistoryComponent, HistoryTimeLineComponent],
  imports: [FormsModule, MaterialModule, ChartsModule, CommonModule, MapModule],
  bootstrap: [HistoryComponent],
})
export class HistoryModule {}
