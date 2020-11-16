import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
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
  events: AudioEvent[] = [];
  constructor(private as: ApiService){}
 ngOnInit(): void {
   this.events = this.as.getEvents(new Date().toString(),new Date().toString()).events;
 }
}
