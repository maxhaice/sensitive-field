import { AfterViewInit, Component, Input } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { LeafletService } from '../../services/leaflet.service';
@Component({
  selector: 'map',
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css']
})
export class MapComponent implements AfterViewInit{
  @Input() sensors: AudioSensor[];
  @Input() events:  BehaviorSubject<AudioEvent[]>;
    map: any;
    constructor(private leaflet: LeafletService){}
    ngAfterViewInit(): void {
      this.map = this.leaflet._init(this.map);
      this.leaflet._updateSensors(this.sensors);
      this.events.subscribe( (_events) =>{
         this.leaflet._updateEvents(_events);
      });
    }
}
