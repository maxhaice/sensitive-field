import {Input, Component, ViewChild, AfterViewInit} from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { MatSlider } from '@angular/material/slider';
import { LatLng } from 'leaflet';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { BehaviorSubject, merge } from 'rxjs';
import { PageDTO } from 'src/root/services/data-transfer/api/interfaces/page-dto.interface';
import {DateAndTimeService} from '../services/date-and-time.service';
import * as moment from 'moment';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'timeline',
  templateUrl: 'timeline.component.html',
  styleUrls: ['timeline.component.css'],
  providers: [DateAndTimeService]
})
export class HistoryTimeLineComponent implements AfterViewInit{
  @Input() selectedAudioEvent: AudioEvent;
  @ViewChild(MatSlider) slider: MatSlider;

  public day: number = new Date().getDate();
  public month: number = new Date().getMonth() + 1;
  public min = 0;
  public maxNumb: number = new Date().getHours() * 60 + new Date().getMinutes();
  public value: number = this.maxNumb;

  private isLoadingResults = false;
  private isNotContent = false;

  public center: BehaviorSubject<LatLng> = new BehaviorSubject<LatLng>(
    new LatLng(0, 0)
  );
  public audioSensors: BehaviorSubject<AudioSensor[]> = new BehaviorSubject<AudioSensor[]>(null);
  public audioEvents: BehaviorSubject<AudioEvent[]> = new BehaviorSubject<AudioEvent[]>(undefined);

  constructor(protected apiService: ApiService, public dateTimeService: DateAndTimeService) {}
  ngAfterViewInit(): void {
    this.apiService
      .getAudioSensors()
      .subscribe((value) => {
        (this.audioSensors.next((value as any)));
        console.log(value);
      });
    this.apiService.getAudioEvents(
      1, 10000, this.dateTimeService.getQueryDate(moment(this.dateTimeService.today()).add(-1, 'm').toDate()),
      this.dateTimeService.getQueryDate(moment(this.dateTimeService.today()).add(1, 'm').toDate())).subscribe(
        ( _: PageDTO<AudioEvent[]>) => {
            this.audioEvents.next((_ as any).audioEventDTOS);
            console.log(_);
    });
    if (this.selectedAudioEvent) {
      this.center.next(
        new LatLng(
          this.selectedAudioEvent.latitude,
          this.selectedAudioEvent.longitude
        )
      );
      this.apiService.getAudioEvents(
        1, 10000, this.dateTimeService.getQueryDate(moment(this.selectedAudioEvent.dateReal).add(-1, 'm').toDate()),
        this.dateTimeService.getQueryDate(moment(this.selectedAudioEvent.dateReal).add(1, 'm').toDate())).subscribe(
        ( _: PageDTO<AudioEvent[]>) => {
          this.audioEvents.next((_ as any).audioEventDTOS);
        });
    }
    this.slider.change.subscribe((value) => {
      if (!this.isLoadingResults){
        this.isLoadingResults = true;
        this.apiService.getAudioEvents(
          1, 10000,
          this.dateTimeService.getQueryDate(moment(this.getCurrentDateAndTime()).add(-1, 'm').toDate()),
          this.dateTimeService.getQueryDate(moment(this.getCurrentDateAndTime()).add(1, 'm').toDate())
        ).subscribe( (_: PageDTO<AudioEvent[]>) => {
          this.audioEvents.next((_ as any).audioEventDTOS);
          this.isLoadingResults = false;
        }, error => {
            this.isLoadingResults = false;
            this.isNotContent = true;
          });
        }});
  }
  private getCurrentDateAndTime(): Date{
    return new Date(this.dateTimeService.today().getFullYear(),
      this.month-1, this.day, Math.floor(this.slider.value / 60), (this.slider.value % 60));
  }
  toBack(): void {
    this.month = this.day === 1 ? this.month - 1 : this.month;
    this.day = this.day !== 1
        ? this.day - 1
        : new Date(new Date().getFullYear(), this.month, 0).getDate();
    this. maxNumb = 24 * 60;
    this.value = this.maxNumb;
  }
  toForward(): void {
    this.maxNumb =
      this.day + 1 !== this.dateTimeService.today().getDate()
        ? 24 * 60
        : new Date().getHours() * 60 + new Date().getMinutes();
    this.value = this.maxNumb;
    const oldMonth = this.month;
    this.month =
      this.day === new Date(new Date().getFullYear(), oldMonth, 0).getDate()
        ? this.month + 1
        : this.month;
    this.day =
      this.day !== new Date(new Date().getFullYear(), oldMonth, 0).getDate()
        ? this.day + 1
        : 1;
  }
}
