import {Injectable, OnInit} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

import {Sensor} from '../interfaces/sensor';
import {Event} from '../interfaces/event';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {FireSound} from './fire/firesound.service';
import { FireAlert } from './fire/firealert.service';

@Injectable()
export class WebSocketAPI {
  private CONNECT_URL = 'http://localhost:8080/connect';
  private ws = new SockJS(this.CONNECT_URL);
  private client = Stomp.over(this.ws);

  public sensors = new BehaviorSubject<Sensor[]>([]);
  public events = new BehaviorSubject<Event[]>([]);

  constructor() {
    try{
      this._connect();
    }
    catch{
      FireAlert.executeError('Невдалося з\'єднатися з сервером.');
    }
  }

  _connect(): void {

    this.client.connect({}, frame => {
      const sessionId = /\/([^\/]+)\/websocket/?.exec(this.ws?._transport?.url)[1];

      this.client.subscribe('/topic/new-event', newEvent => {
        FireSound.execute();
        const arr = this.events.getValue();
        const event = JSON.parse(newEvent.body) as Event;
        arr.push(event);
        this.events.next(arr);
      });

      this.client.subscribe(
        '/topic/sensor-appeared',
          sensorAppeared => this._updateSensors()
      );

      this.client.subscribe('/topic/sensor-disappeared', sensorDisappeared => {
        this._updateSensors();
      });

      this.client.subscribe('/topic/get-last-events' + sessionId, lastEvents => {
        this.events.next(JSON.parse(lastEvents.body) as Event[]);
      });

      this.client.subscribe('/topic/get-sensors' + sessionId, sensors => {
        this.sensors.next(JSON.parse(sensors.body) as Sensor[]);
      });
      this._updateSensors();
    }, this.errorCallBack);
  }

  _updateEvents(count: number): void {
    this.client.send("/app/get-last-events", {}, JSON.stringify({'count': count}));
  }

  _updateSensors(): void {
    this.client.send('/app/get-sensors', {});
  }

  _disconnect(): void {
    if (this.client !== null) {
      this.client.disconnect();
    }
  }

  errorCallBack(error): void {
    setTimeout(() => {
      try{
      this._connect();
      }
      catch{
        FireAlert.executeError('Невдалося з\'єднатися з сервером.');
      }
    }, 5000);
  }
}
