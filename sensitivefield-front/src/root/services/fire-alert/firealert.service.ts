import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class FireAlert{

  public static alert = new BehaviorSubject<string>('');
  public static error = new BehaviorSubject<string>('');
  constructor() {}
     static execute(info: string): any{
       return this.alert.next(info);
     }
     static executeError(error: string): any{
      return this.error.next(error);
    }
}
