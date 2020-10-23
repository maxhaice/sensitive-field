import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import * as Leaflet from 'leaflet';
import { interval } from 'rxjs';
import { takeWhile } from 'rxjs/operators';
import {Sensor} from '../../../interfaces/sensor';
import {Event} from '../../../interfaces/event';
import {WebSocketAPI} from '../../../services/websocket.connector.service';
import { FireAlert } from 'src/app/services/fire/firealert.service';
@Component({
  selector: 'map',
  templateUrl: 'map.component.html',
  styleUrls: ['map.component.css'],
  providers: [WebSocketAPI]
})
export class MapComponent implements AfterViewInit{
  private map: any;
  private sensors = Leaflet.layerGroup();
  private sounds = Leaflet.layerGroup();

  constructor(private webSocket: WebSocketAPI) {
    // webSocket._connect();
  }

  ngAfterViewInit(): void {
    // this.webSocket._updateEvents(10);
    this.map = Leaflet.map('map', {
      center: [ 50.450001, 30.523333 ],
      zoom: 15
    });
    // this.setMarker(50.450001, 30.527333, 'assets/sound.png',  '', true, true)
      Leaflet.tileLayer('assets/tiles/{z}/{x}/{y}.png', {
        maxZoom: 15,
        attribution: 'The HUB'}).addTo(this.map).addEventListener("tileerror",error=>{
          FireAlert.executeError('Невдалося показати тайтл карти.');
        });
    this.sensors.addTo(this.map);
    this.sounds.addTo(this.map);

    this.webSocket.sensors.subscribe(value => {
      this.sensors.clearLayers();
      this.showSensors(value as Sensor[]);
    });
    this.webSocket.events.subscribe(value => {
      this.showEvents(value as Event[]);
    });
  }

  private showSensors(arr: Sensor[]): void{
    arr.forEach(sens => {
      if (!sens) return;
      this.setMarker(sens.latitude, sens.longitude, sens.state ? 'assets/active_sensor.png' : 'assets/desactive_sensors.png', sens.id + '', true, false);
    })
  }

  private showEvents(arr: Event[]): void{
    arr.forEach(ev => {
      console.log(ev.latitude+";"+ev.longitude);
      // const d = new Date();
      // const dateNow = d.getFullYear() + '-' + d.getMonth() + '-' + d.getDay() + ' ' + d.getHours() + ':'
      // 2020-10-07 18:17:54
      // TODO: need to see how time look like, and edit like ev.dateReal.seconds in period ten seconds
      // if(ev.dateReal.split()) return;
    let eventOnMap =  (this.setMarker(ev.latitude, ev.longitude, 'assets/sound.png', ev.id + '', true, true) as Leaflet.Circle);
    let stop = false;
    interval(10000).pipe(takeWhile(() => !stop)).subscribe(() => {
      if (eventOnMap.options.fillOpacity === .3){
        interval(3000).pipe(takeWhile(() => eventOnMap.options.fillOpacity !== .0)).subscribe(() => {
          eventOnMap.options.fillOpacity -= .1;
        });
      }
      else{
        stop = true;
        // this.sounds = this.sounds.filter(obj => obj !== ev);
        this.sensors.removeLayer(eventOnMap);
      }
    });
    });
  }

  private setMarker(latitude: number, s: number, url: string, id: string, field: boolean, sound: boolean): any{
   Leaflet.marker([latitude, s], {
      icon: Leaflet.icon(
        {iconUrl: url,
        iconSize:     [30, 20],
        shadowSize:   [30, 20],
        iconAnchor:   [14.3, 10],
        shadowAnchor: [10, 9],
        popupAnchor:  [0, -30]})}).addTo(sound ? this.sounds : this.sensors).bindPopup(id);
    if (field) {
     return Leaflet.circle([latitude, s], {
        color: 'lightgrey',
        fillColor: '#f03',
        fillOpacity: 0.3,
        radius: 100
      }).addTo(sound ? this.sounds : this.sensors);
    }
    return;
  }
}
