import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HTTPService } from '../../http/http.service';
import { SubscribeObject } from '../interfaces/subscribe.object.interface';

@Injectable({
    providedIn: 'root'
})
export class ApiSubscriber {
    private subscribers: SubscribeObject<any>[] = [];
    constructor(private httpService: HTTPService){
    }
    subscribe<T>( type: string ): Observable<T>  {
        if (!this.subscribers.find((_) => _.type === type)){
            this.subscribers.push({
                type,
                subscriber: this.httpService.get<T>(type)
            } as SubscribeObject<T>);
        }
        return this.subscribers.find((_) => _.type === type).subscriber;
    }
}
