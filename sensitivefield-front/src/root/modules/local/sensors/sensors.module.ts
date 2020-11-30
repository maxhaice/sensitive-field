import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../environment/md.module';
import { SensorsComponent } from './components/sensors.component/sensors.component';

@NgModule({
    declarations: [SensorsComponent],
    imports: [FormsModule, MaterialModule, CommonModule],
    bootstrap: [SensorsComponent]
})
export class SensorsModule {}