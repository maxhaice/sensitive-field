import { Observable } from 'rxjs';

export interface SubscribeObject<T>{
    type: string;
    subscriber: Observable<T>;
}