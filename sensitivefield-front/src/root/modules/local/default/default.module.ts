import { NgModule } from '@angular/core';
import { MaterialModule } from '../../environment/md.module';
import { DefaultComponent } from './components/default.component/default.component';

@NgModule({
    declarations: [DefaultComponent],
    imports: [MaterialModule],
    exports: [DefaultComponent]
})
export class DefaultModule {}
