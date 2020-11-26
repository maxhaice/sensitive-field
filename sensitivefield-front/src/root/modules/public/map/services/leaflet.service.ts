import {Injectable } from '@angular/core';

import * as Leaflet from 'leaflet';
import { LatLng } from 'leaflet';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
export { Leaflet };

@Injectable({
    providedIn: 'root'
})
export class LeafletService {
    constructor(private as: ApiService){}
    private  _activeSensorImage = 'assets/active_sensor.png';
    private  _eventImage = 'assets/sound.png';

    private  map: any;
    private  sensors = Leaflet.layerGroup();
    private  events = Leaflet.layerGroup();

    public _map(){
        
    }
    public _init(map: any, center: LatLng): any{
          map = Leaflet.map('map', {
            center: [ center.lat, center.lng ],
            zoom: 15});
        Leaflet.tileLayer('assets/tiles/{z}/{x}/{y}.png', {
            maxZoom: 15,
            attribution: 'The HUB'}).addTo(map);
            //TODO: firealert!
        this.sensors.addTo(map);
        this.events.addTo(map);
        return map;
    }

    public _updateSensors(sensors: AudioSensor[]){
        sensors?.forEach((sensor)=>{
            Leaflet.marker([sensor.latitude, sensor.longitude], {
                icon: Leaflet.icon(
                  {iconUrl: this._activeSensorImage,
                  iconSize:     [30, 20],
                  shadowSize:   [30, 20],
                  iconAnchor:   [14.3, 10],
                  shadowAnchor: [10, 9],
                  popupAnchor:  [0, -30]})}).addTo(this.sensors).bindPopup(sensor.name? sensor.name : sensor.id.toString());
        });
    }
    public  _updateEvents(events: AudioEvent[]){
        events?.forEach((event)=>{
            // TODO: categories? that is it? and how it work.
            this._center(new Leaflet.LatLng(event.latitude,event.longitude));
            let mark = Leaflet.marker([event.latitude, event.longitude], {
                icon: Leaflet.icon(
                  {iconUrl: this._eventImage,
                  iconSize:     [30, 20],
                  shadowSize:   [30, 20],
                  iconAnchor:   [14.3, 10],
                  shadowAnchor: [10, 9],
                  popupAnchor:  [0, -30]})}).addTo(this.events).bindPopup(event.id.toString());
            let circleMark = Leaflet.circle([51.508, -0.11], {
                    color: 'red',
                    fillColor: '#f03',
                    fillOpacity: 0.5,
                    radius: 500
                }).addTo(this.events);
            setTimeout(() => { 
                clearInterval(setInterval(() => circleMark.options.fillOpacity-=0.1, 2000)); 
                this.events.removeLayer(circleMark);
                this.events.removeLayer(mark);
            }, 10000);
        });
    }
    public _center(latlng: Leaflet.LatLngExpression){
        this.map?.panTo(latlng);
    }
}
