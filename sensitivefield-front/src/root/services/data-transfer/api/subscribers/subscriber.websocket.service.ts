import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WebSocketService } from '../../websocket/websocket.service';
import { SubscribeObject } from '../interfaces/subscribe.object.interface';

@Injectable({
    providedIn: 'root'
})
export class WebSocketSubscriber {
    public tieSubscribers: SubscribeObject<any>[] = [];
    constructor(private _ws: WebSocketService){
    }
    subscribe<T>( type: string ): Observable<T>  {
        if (!this.tieSubscribers.find((_)=>_.type===type)){
            this.tieSubscribers.push({
                type: type,
                subscriber: this._ws.tie(type)
            } as SubscribeObject<T>);
        }
        return this.tieSubscribers.find((_)=>_.type===type).subscriber;
    }


}