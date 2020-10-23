import { Component, ElementRef } from '@angular/core';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';
import { ToastrService } from 'ngx-toastr';
import { FireAlert } from 'src/app/services/fire/firealert.service';

@Component({
  selector: 'router',
  templateUrl: './_router.component.html',
  styleUrls: ['./_router.component.css'],
  providers: [FireCommunication, ToastrService]
})

export class Router {
  private authority: string = "";
  navbar: boolean; 
 
  constructor(private tokenStorage: TokenStorageService,private toastr: ToastrService) {}
  ngOnInit() {
    FireCommunication.onInit();
    FireAlert.alert.subscribe(value=>{
      if (value=='') return;
      this.toastr.info(value+"", 'Інформування:', {
        timeOut: 3000,
      });
    });
    FireAlert.error.subscribe(error=>{
      if (error=='') return;
      this.toastr.error(error+"", 'Помилка виконання:', {
        timeOut: 3000,
      });
    });
    FireCommunication.isHide().subscribe(value=>{
      this.navbar = value;
    })
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
    }
  }
}
