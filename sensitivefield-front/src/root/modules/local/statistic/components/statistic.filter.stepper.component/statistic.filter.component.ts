import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { ChartOptions } from '../../interfaces/options.interface';
import * as moment from 'moment';

@Component({
  selector: 'stat-filter',
  templateUrl: 'statistic.filter.component.html',
  styleUrls: ['statistic.filter.component.css'],
})
export class StatisticFilterStepperComponent implements OnInit {
  @Output() options: EventEmitter<ChartOptions> = new EventEmitter<ChartOptions>();

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  timeFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder) {}

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      opt1: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      opt2: ['', Validators.required]
    });
    this.timeFormGroup = this._formBuilder.group({
      opt3: ['', Validators.required],
      opt4: ['', Validators.required]
    });
  }
  timeFilterStart = (d: Date | null): boolean => {

    return (((d || new Date()).getMonth()<(new Date()).getMonth() ||
           ((d || new Date()).getMonth()==(new Date()).getMonth() && d.getDate() <= new Date().getDate())) &&
           (d.getFullYear()<=new Date().getFullYear())) && (d.getFullYear() == new Date().getFullYear());
  }
  timeFilterEnd = (d: Date | null): boolean => {

    return (this.timeFormGroup.controls['opt3'].valid) && (((d || new Date()).getMonth()<(new Date()).getMonth() ||
           ((d || new Date()).getMonth()==(new Date()).getMonth() && d.getDate() <= new Date().getDate())) &&
           (d.getFullYear()<=new Date().getFullYear())) &&
           (
            (d.getMonth()==(this.timeFormGroup.controls['opt3'].value as Date).getMonth() && d.getDay()<(this.timeFormGroup.controls['opt3'].value as Date).getDay()) ||
            (d.getMonth()<(this.timeFormGroup.controls['opt3'].value as Date).getMonth())
           ) && (d.getFullYear() == new Date().getFullYear());
  }
  saveOptions(): void{
      if(this.timeFormGroup.invalid) return;
      //TODO: save to storage
    this.options.next({
        opt1: this.firstFormGroup.controls['opt1'].value,
        opt2: this.secondFormGroup.controls['opt2'].value,
        opt3: {
           start: moment(this.timeFormGroup.controls['opt3'].value).add(23, 'h').toDate(),
           end: this.timeFormGroup.controls['opt4'].value
        }
    });
  }
}
