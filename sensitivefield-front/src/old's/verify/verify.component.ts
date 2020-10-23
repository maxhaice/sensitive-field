import { Component, HostBinding } from '@angular/core';

@Component({
  selector: 'verify',
  templateUrl: 'verify.component.html',
  styleUrls: ['verify.component.css']
})
export class Verify {
  @HostBinding('style.display') visible;

  cheackPassword(ev):void{
    if ( ev.target.value == "password" ){
      this.visible = "none";
     }
    else console.log(ev.target.value);
  }
}
