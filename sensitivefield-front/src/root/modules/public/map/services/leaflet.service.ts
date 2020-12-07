import {Injectable } from '@angular/core';

import * as Leaflet from 'leaflet';
import { LatLng } from 'leaflet';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';

@Injectable()
export class LeafletService {
    constructor(){}
    private  activeSensorImage = 'assets/images/active_sensor.png';
    private  eventImage = 'assets/images/sound.png';

    private  map: any;
    private  live: boolean;
    private  sensors = Leaflet.layerGroup();
    private  events = Leaflet.layerGroup();

    public _init(map: any, center: LatLng, live: boolean): any{
          map = Leaflet.map('map', {
            center: [ center.lat, center.lng ],
            zoom: 18});
          //if u want - use it for map test
          // Leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          Leaflet.tileLayer('assets/tiles/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: 'The HUB'}).addTo(map);
          this.sensors.addTo(map);
          this.events.addTo(map);
          this.map = map;
          this.live = live;
          return map;
    }

    public _updateSensors(sensors?: AudioSensor[], sensor?: AudioSensor): void{
      if (!sensors && sensor){
        sensors = [];
        sensors.push(sensor);
      }
      sensors?.forEach((sen) => {
            Leaflet.marker([sen.latitude, sen.longitude], {
                icon: Leaflet.icon(
                  {iconUrl: this.activeSensorImage,
                  iconSize:     [30, 20],
                  shadowSize:   [30, 20],
                  iconAnchor:   [14.3, 10],
                  shadowAnchor: [10, 9],
                  popupAnchor:  [0, -30]})}).addTo(this.sensors).bindPopup(sen.name ? sen.name : sen.id.toString());
            Leaflet.circle([sen.latitude, sen.longitude], {
                  color: 'green',
                  fillColor: '#006',
                  fillOpacity: 0.5,
                  radius: 350}).addTo(this.sensors);
        });
    }
    public  _updateEvents(event?: AudioEvent, events?: AudioEvent[]): void{
            if (events) {
              this.events.clearLayers();
              events.forEach(value => {
                this.addAudioEvent(value);
              });
              return;
            }
            else if (event) {
              this.addAudioEvent(event);
              return;
            }
    }
    private addAudioEvent(event: AudioEvent): void {
      this._center(new LatLng(event.latitude, event.longitude));
      const mark = Leaflet.marker([event.latitude, event.longitude], {
        icon: Leaflet.icon(
          {iconUrl: this.eventImage,
            iconSize:     [30, 20],
            shadowSize:   [30, 20],
            iconAnchor:   [14.3, 10],
            shadowAnchor: [10, 9],
            popupAnchor:  [0, -30]})}).addTo(this.events).bindPopup(event.id.toString());
      const circleMark = Leaflet.circle([event.latitude, event.longitude], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 150
      }).addTo(this.events);
      if (this.live) {
        setTimeout(() => {
          clearInterval(setInterval(() => [], 2000));
          this.events.removeLayer(circleMark);
          this.events.removeLayer(mark);
        }, 10000);
      }
    }
    public _center(latlng: Leaflet.LatLngExpression): void{
      this.map.panTo(latlng);
    }
}
export { Leaflet };
