import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { HTTPService } from '../http/http.service';
import { WebSocketService } from '../websocket/websocket.service';
import { DTO } from './interfaces/dto.interface';
import { PageDTO } from './interfaces/page-dto.interface';
import { EVENTS, NEW_EVENTS, SENSORS } from './subscribers/subscribe-const/const.subscribe';
import { Subscriber } from './subscribers/subscriber.service';

@Injectable({
    providedIn: 'root'
})
export class ApiService{
    private audioEventsDTO: DTO<AudioEvent>;
    constructor(private _ws: WebSocketService, private _sb: Subscriber){
        this.tieEventsInTouch();
    }
    private tieEventsInTouch(): DTO<AudioEvent> {
        this._ws._toDictionary(NEW_EVENTS,false,(value)=>{
            this.audioEventsDTO.newValues.push(value);
            this.audioEventsDTO.allValues.push(value);
        });
        return this.audioEventsDTO;
    }
    public getEvents(dateStart: string, dateEnd: string): Observable<AudioEvent[]>{
       return this._sb.subscribe<AudioEvent[]>(EVENTS + '?date=' + dateStart);
    }
    public getSensors(): Observable<AudioSensor[]>{
       return this._sb.subscribe<AudioSensor[]>(SENSORS);
    }
    public getSensorsByPage(page: number): Observable<PageDTO<AudioSensor[]>> {
        return this._sb.subscribe<PageDTO<AudioSensor[]>>(SENSORS+'/page/'+page);

    }
}
