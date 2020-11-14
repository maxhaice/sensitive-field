import { Injectable } from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Injectable()
export class FireAlert{

  public static alert = new BehaviorSubject<String>('');
  public static error = new BehaviorSubject<String>('');
  constructor() {}
     static execute(info: string){
       return this.alert.next(info);
     }
     static executeError(error: string){
      return this.error.next(error);
    }
}
