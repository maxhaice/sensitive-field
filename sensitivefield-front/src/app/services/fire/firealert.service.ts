import {Component, Injectable, OnInit} from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Injectable()
export class FireAlert{

  public static alert = new BehaviorSubject<String>('');
  public static error = new BehaviorSubject<String>('');
  constructor() {}
  // static executeComponent(value?:Component){
  //   this.alert.next(value);
  // }
  // static executeWarning(value?:Component){
  //   this.alert.next(value);
  // }
     static execute(info: string){
      //  return this.alert.next(info);
     }
     static executeError(error: string){
      // return this.error.next(error);
    }
}
