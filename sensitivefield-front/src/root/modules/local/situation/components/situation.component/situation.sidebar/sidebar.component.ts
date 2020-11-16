import { Component, Input, OnInit } from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';

@Component({
  selector: 'situation-sidebar',
  templateUrl: 'sidebar.component.html',
  styleUrls: ['sidebar.component.css'],
  providers: [ApiService]
})
export class SituationSideBarComponent implements OnInit{
  @Input() events: AudioEvent[] = [];
  sensors: AudioSensor[] = [];
  
  private currentPageSensors: number = 1;
  constructor(private as: ApiService){}
 ngOnInit(): void {
   this.sensors = this.as.getSensorsByPage(this.currentPageSensors)?.sensors;
 }
 scrollSensors(scroll: any): void {
  if (scroll.target.offsetHeight + scroll.target.scrollTop >= scroll.target.scrollHeight) {
      this.as.getSensorsByPage(++this.currentPageSensors)?.sensors.forEach(x=>this.sensors.push(x));
  }
 }
}
