import { Injectable } from '@angular/core';
import { RxStomp } from '@stomp/rx-stomp/esm6/rx-stomp';

import { Observable } from 'rxjs';
import { TokenStorageService } from '../../local-storage/token.storage.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private brokerURL = 'ws://localhost:8080/connect';

  private rxStomp: RxStomp;
  
  constructor(private _ts: TokenStorageService){
    if(this._ts.getToken()){
      this.rxStomp = new RxStomp();
      this.rxStomp.configure({
         brokerURL: this.brokerURL,
         connectHeaders: {
         login: this._ts.getUsername(),
         passcode: '//password'
         },
         heartbeatIncoming: 0,
         heartbeatOutgoing: 20000,
         reconnectDelay: 10000,
         debug: ()=>{
           //TODO: FireAlert
         }
        });
      this.rxStomp.activate();
    }
  }
  public tie(type: string): Observable<any> {
    return this.rxStomp.watch(type);
  }
}