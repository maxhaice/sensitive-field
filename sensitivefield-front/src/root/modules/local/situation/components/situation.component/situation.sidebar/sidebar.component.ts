import { Component, Input, OnInit } from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import {Router} from '@angular/router';
import {LatLng} from 'leaflet';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'situation-sidebar',
  templateUrl: 'sidebar.component.html',
  styleUrls: ['sidebar.component.css'],
  providers: [ApiService]
})
export class SituationSideBarComponent implements OnInit{
  @Input() eventNew: BehaviorSubject<AudioEvent>;
  @Input() sensor: BehaviorSubject<AudioSensor>;
  events: AudioEvent[] = [];
  @Input() center: BehaviorSubject<LatLng> = new BehaviorSubject<LatLng>(new LatLng(0.0, 0.0));
  sensors: AudioSensor[] = [];

  private currentPageSensors: number = 1;
  constructor(private as: ApiService, private _router: Router){}
 ngOnInit(): void {
    this.eventNew.subscribe(value => {
      if (value!= null){
        this.events.push(value);
      }
    });
    this.sensor.subscribe(value => {
      this.as.getAudioSensors(1, 10)?.subscribe(allSensors =>{
        this.sensors = allSensors.audioSensors;
      } );
    });
    this.as.getAudioEvents(this.currentPageSensors)?.subscribe(allSensors =>{
     this.events = (allSensors as any).audioEventDTOS;
   });
   this.as.getAudioSensors(1, 10)?.subscribe(allSensors =>{
     this.sensors = allSensors.audioSensors;
   } );

 }
 scrollSensors(scroll: any): void {
  if (scroll.target.offsetHeight + scroll.target.scrollTop >= scroll.target.scrollHeight) {
      this.as.getAudioSensors(this.currentPageSensors)?.subscribe(newSensors => {
        newSensors.audioSensors.forEach(value => this.sensors.push(value));
        this.currentPageSensors++;
      });
  }
 }
 toCenter(ev: any): void{
    this.center.next(new LatLng(ev.latitude, ev.longitude));
 }
}
