import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { DTO } from './interfaces/dto.interface';
import { PageDTO } from './interfaces/page-dto.interface';
import {EVENTS, NEW_EVENTS, NEW_SENSORS, SENSORS} from './subscribers/subscribe-const/const.subscribe';
import { ApiSubscriber } from './subscribers/subscriber.api.service';
import { WebSocketSubscriber } from './subscribers/subscriber.websocket.service';

@Injectable({
    providedIn: 'root'
})
export class ApiService{
    constructor(private webSocketSubscriber: WebSocketSubscriber, private apiSubscriber: ApiSubscriber){}

    public tieAudioEvents(): Observable<DTO<AudioEvent>>{
      return this.webSocketSubscriber.subscribe<DTO<AudioEvent>>(NEW_EVENTS);
    }
  public tieAudioSensors(): Observable<DTO<AudioSensor>>{
    return this.webSocketSubscriber.subscribe<DTO<AudioSensor>>(NEW_SENSORS);
  }
    public getAudioEvents(page?: number, pageSize?: number, dateAfter?: string,
                          dateBefore?: string, orderBy?: string, orderDirection?: boolean,
                          search?: string, filters?: string): Observable<PageDTO<AudioEvent[]>>{
        return this.apiSubscriber.subscribe<PageDTO<AudioEvent[]>>(EVENTS + `?` +
            (page ? `page=${page - 1}` : ``) +
            (pageSize ? `&pageSize=${pageSize}` : ``) +
            (dateAfter ? `&dateAfter=${dateAfter}` : ``) +
            (dateBefore ? `&dateBefore=${dateBefore}` : ``) +
            (orderBy ? `&order_by=${orderBy}` : ``) +
            (orderDirection ? `&order_direction=${orderDirection}` : ``) +
            (search ? `&search=${search}` : ``) +
            (filters ? `&filters=${filters}` : ``));
    }
    public getAudioSensors(page?: number, pageSize ?: number, orderBy?: string,
                           orderDirection?: boolean, search?: string, filters?: string): Observable<PageDTO<AudioSensor[]>>{
       return this.apiSubscriber.subscribe<PageDTO<AudioSensor[]>>(SENSORS + `?` +
            (page ? `page=${page - 1}` : ``) +
            (pageSize ? `&pageSize=${pageSize}` : ``) +
            (orderBy ? `&order_by=${orderBy}` : ``) +
            (orderDirection ? `&order_direction=${orderDirection}` : ``) +
            (search ? `&search=${search}` : ``) +
            (filters ? `&filters=${filters}` : ``));
    }
}
