import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../../../services/auth/token.storage.service";
import {Router} from "@angular/router";
@Component({
  selector: 'system',
  templateUrl: 'system.component.html',
  styleUrls: ['system.component.css']
})
export class SystemRout implements OnInit{
  public authority: string;
  constructor(private tokenStorage: TokenStorageService, private router: Router) {}
  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      if (!this.authority) {
        if (this.authority === 'ROLE_USER') {
          this.router.navigate(['']);
        }
      }
    }
    else {
      this.router.navigate(['']);
    }
  }
}
