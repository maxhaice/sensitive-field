import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';

@Component({
  selector: 'situation-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css'],
  providers: [ApiService]
})
export class SituationNavbarComponent implements OnInit{
  audioEvents: AudioEvent[] = [];
   constructor(private as: ApiService){}
  ngOnInit(): void {
  }
}
