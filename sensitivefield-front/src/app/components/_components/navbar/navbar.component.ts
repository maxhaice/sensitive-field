import {Component, OnInit} from '@angular/core';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';

@Component({
  selector: 'navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css'],
  providers: [FireCommunication]
})

export class NavbarComponent implements OnInit{
  authority: string = "";
  navbar: boolean;
 
  constructor(private tokenStorage: TokenStorageService) { }
 
  ngOnInit() {
    FireCommunication.isHide().subscribe(value=>{
      this.navbar = value;
    })
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
    }
    else this.authority = 'admin';
  }
}
