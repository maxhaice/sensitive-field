import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { LatLng } from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { LeafletService } from '../../services/leaflet.service';
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'map',
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css'],
  providers: [LeafletService],
})
export class MapComponent implements AfterViewInit {
  @Input() sensors: BehaviorSubject<AudioSensor[]>;
  @Input() sensor?: BehaviorSubject<AudioSensor>;
  @Input() events?: BehaviorSubject<AudioEvent>;
  @Input() event?: BehaviorSubject<AudioEvent[]>;
  @Input() center: BehaviorSubject<LatLng>;
  @Input() live?: boolean = true;
  map: any;
  constructor(private leaflet: LeafletService) {}
  ngAfterViewInit(): void {
    this.map = this.leaflet._init(this.map, this.center.value, this.live);
    this.sensors.subscribe(( sensors) => {
      this.leaflet._updateSensors(sensors);
    });
    this.sensor?.subscribe(( _) => {
      this.leaflet._updateSensors(undefined, _);
    });
    this.events?.subscribe(( event) => {
      if (event == null) {
        return;
      }
      this.leaflet._updateEvents(event);
    });
    this.event?.subscribe(_ => {
      this.leaflet._updateEvents(undefined, _);
    });
    this.center.subscribe((center) => {
      this.leaflet._center(center);
    });
  }
}
