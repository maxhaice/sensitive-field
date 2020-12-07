import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../environment/md.module';
import { MapComponent } from './components/map.component/map.component';

@NgModule({
    declarations: [MapComponent],
    imports: [FormsModule, MaterialModule],
    exports: [MapComponent],
    bootstrap: [MapComponent]
})
export class MapModule {}
