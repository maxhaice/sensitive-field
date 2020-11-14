import { Injectable } from '@angular/core';

import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { DataTransferDictionaryItem } from './interfaces/dictionary.interface';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private CONNECT_URL = 'http://localhost:8080/connect';

  private _WS = new SockJS(this.CONNECT_URL);
  private _client = Stomp.over(this._WS);

  private _dictionary: DataTransferDictionaryItem[] = [];
  
  constructor(){
    this._connect();
  }
  public _toDictionary(url: string, isSession: boolean, fun: Function): any{
    this._dictionary.push({
        url: url,
        isSession: isSession,
        fun: fun
    });
    this._reconnect();
  }
  private _connect(): void {
    this._client.connect({}, () => {
      const sessionId = /\/([^\/]+)\/websocket/?.exec(this._WS?._transport?.url)[1];
        if ( this._dictionary.length>0 ){
            this._dictionary.forEach(item=>{
                this._client.subscribe(item.url+(item.isSession?sessionId:''),item.fun());
            });
        }
    }, this._errorCallBack);
  }
  private _reconnect(): void{
      if (this._disconnect()) return;
        this._connect();
  }
  private _disconnect(): boolean {
    if (this._client !== null){
      this._client.disconnect();
      return true;
    }
    return false; 
  }

  private _errorCallBack(): void {
    setTimeout(() => {
      try{
        this._connect();
      }
      catch{
      }
    }, 5000);
  }
}