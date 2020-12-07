import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { MaterialModule } from '../../environment/md.module';
import { StatisticComponent } from './components/statistic.component/statistic.component';
import { StatisticFilterStepperComponent } from './components/statistic.filter.stepper.component/statistic.filter.component';
import { MomentModule } from 'ngx-moment';
@NgModule({
    declarations: [StatisticComponent,StatisticFilterStepperComponent],
    imports: [FormsModule, MaterialModule, ChartsModule, FormsModule, ReactiveFormsModule, CommonModule, MomentModule],
    bootstrap: [StatisticComponent]
})
export class StatisticModule {}