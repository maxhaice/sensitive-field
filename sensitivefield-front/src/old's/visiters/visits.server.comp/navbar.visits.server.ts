import { Component, OnInit, } from '@angular/core';
import { CommunicationService } from '../../../services/app.service'

@Component({
  selector: 'nav-bar-SV',
  templateUrl: 'navbar.visits.server.html',
  styleUrls: ['navbar.visits.server.css']

})

export class NavBarVS implements OnInit {
    constructor(private service: CommunicationService) { }

    ngOnInit(): void { }
    change(ev){

    }
    onClickGet(num){
        this.service.set(num);
    }
}
