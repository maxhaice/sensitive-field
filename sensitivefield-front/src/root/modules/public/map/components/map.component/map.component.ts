import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { LatLng } from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { LeafletService } from '../../services/leaflet.service';
@Component({
  selector: 'map',
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css']
})
export class MapComponent implements AfterViewInit, OnInit{
  @Input() sensors: AudioSensor[];
  @Input() events:  BehaviorSubject<AudioEvent[]>;
  @Input() center:  BehaviorSubject<LatLng>;
    map: any;
    constructor(private leaflet: LeafletService){}
  ngOnInit(): void {
  }
  ngAfterViewInit(): void {
      this.map = this.leaflet._init(this.map, this.center.value);
      this.leaflet._updateSensors(this.sensors);
      this.events.subscribe( (_events) =>{
         this.leaflet._updateEvents(_events);
      });
      this.center.subscribe((_center)=>{
        this.leaflet._center(_center);
      })
  }
}
