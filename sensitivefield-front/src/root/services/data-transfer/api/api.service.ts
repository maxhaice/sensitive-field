import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { DTO } from './interfaces/dto.interface';
import { PageDTO } from './interfaces/page-dto.interface';
import { EVENTS, NEW_EVENTS, SENSORS } from './subscribers/subscribe-const/const.subscribe';
import { ApiSubscriber } from './subscribers/subscriber.api.service';
import { WebSocketSubscriber } from './subscribers/subscriber.websocket.service';
@Injectable({
    providedIn: 'root'
})
export class ApiService{
    constructor(private _ws: WebSocketSubscriber, private _sb: ApiSubscriber){}

    public tieAudioEvents(): Observable<DTO<AudioEvent>>{
      return this._ws.subscribe<DTO<AudioEvent>>(NEW_EVENTS);
    }
    public getAudioEvents(page?: number, per_page?: number, order_by?: string, order_direction?: boolean, search?: string, filters?: string): Observable<PageDTO<AudioEvent[]>>{
        return this._sb.subscribe<PageDTO<AudioEvent[]>>(EVENTS+`?`+
            (page?`page=${page+1}`:``)+
            (per_page?`&per_page=${per_page}`:``)+
            (order_by?`&order_by=${order_by}`:``)+
            (order_direction?`&order_direction=${order_direction}`:``)+
            (search?`&search=${search}`:``)+
            (filters?`&filters=${filters}`:``));
    }
    public getAudioSensors(page?: number, per_page?: number, order_by?: string, order_direction?: boolean, search?: string, filters?: string): Observable<PageDTO<AudioSensor[]>>{
       return this._sb.subscribe<PageDTO<AudioSensor[]>>(SENSORS+`?`+
            (page?`page=${page+1}`:``)+
            (per_page?`&per_page=${per_page}`:``)+
            (order_by?`&order_by=${order_by}`:``)+
            (order_direction?`&order_direction=${order_direction}`:``)+
            (search?`&search=${search}`:``)+
            (filters?`&filters=${filters}`:``));
    }
}
