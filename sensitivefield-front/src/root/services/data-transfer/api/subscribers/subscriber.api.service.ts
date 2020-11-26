import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HTTPService } from '../../http/http.service';
import { ApiService } from '../api.service';
import { SubscribeObject } from '../interfaces/subscribe.object.interface';

@Injectable({
    providedIn: 'root'
})
export class ApiSubscriber {
    private _subscribers: SubscribeObject<any>[] = [];
    constructor(private _http: HTTPService){
    }
    subscribe<T>( type: string ): Observable<T>  {
        if (!this._subscribers.find((_)=>_.type===type)){
            this._subscribers.push({
                type: type,
                subscriber: this._http.get<T>(type)
            } as SubscribeObject<T>);
        }
        return this._subscribers.find((_)=>_.type===type).subscriber;
    }
}