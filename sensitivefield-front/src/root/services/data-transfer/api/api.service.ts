import { Injectable } from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { HTTPService } from '../http/http.service';
import { WebSocketService } from '../websocket/websocket.service';
import { EventSystem } from './interfaces/eventsystem.interface';
import { SensorSystem } from './interfaces/sensor.system.interface';

@Injectable({
    providedIn: 'root'
})
export class ApiService{
    constructor(private _ws: WebSocketService, private _http: HTTPService){
        this.tieEventsInTouch();
    }
    public _es: EventSystem;
    public _ss: SensorSystem;
    private tieEventsInTouch(): EventSystem {
        this._ws._toDictionary("/topic/new-event",false,(value)=>{
            this._es.eventOnTouch.push(value);
        });
        return this._es;
    }
    public getEvents(dateStart: string, dateEnd: string): EventSystem{
        this._http.get<AudioEvent[]>('http://localhost:8080/api/history?date=' + dateStart).subscribe(done => {
                  this._es.events = done;
                }, er => {
                });
        return this._es;
    }
    public getSensors(): SensorSystem{
        this._http.get<AudioEvent[]>('/sensors').subscribe(done => {
                  this._ss.sensors = done;
                }, er => {
                });
        return this._ss;
    }
    public getSensorsByPage(page: number): SensorSystem {
        this._http.get<any>('/page/' + page).subscribe(done => {
            this._ss.sensors = done.audioSensors;
          }, er => {
          });
        return this._ss;
    }
}
